package com.icia.board.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLEncoder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.icia.board.dao.BoardDao;
import com.icia.board.dto.BoardFile;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileManager {

	@Autowired
	private BoardDao bDao;
    //private fullPath="c:/dskjfkdsjfkldsjflksdjfksdjfkls/upload"
	// 파일 업로드, DB저장
	public boolean fileUpload(List<MultipartFile> attachments, HttpSession session, int b_num) {
		log.info("FileManager class");
		// 프로젝트의 upload경로 찾기
		String realPath = session.getServletContext().getRealPath("/");
		log.info("realPath={}", realPath);
		realPath += "upload/";
		log.info("realPath={}", realPath);

		// 2.폴더 생성을 꼭 할것...
		File dir = new File(realPath);
		if (dir.isDirectory() == false) { // upload폴더 없다면
			dir.mkdir(); // upload폴더 생성
		}
		//파일의 정보를 BoardFile or HashMap에 저장
		Map<String, String> fMap = new HashMap<String, String>();
		fMap.put("b_num", String.valueOf(b_num));  
		//System.out.println("size:"+attachments.size());
		boolean result = false;
		for( MultipartFile mf : attachments) {
			// 파일 메모리에 저장
			String oriFileName = mf.getOriginalFilename(); // a.txt
			if(oriFileName.equals("")){
				return false;
			}
			System.out.println("원조 파일:"+oriFileName);
			fMap.put("oriFileName", oriFileName);
			// 4.시스템파일이름 생성 a.txt ==>112323242424.txt
			String sysFileName = System.currentTimeMillis() + "."
					+ oriFileName.substring(oriFileName.lastIndexOf(".") + 1);
			System.out.println("서버 파일:"+sysFileName);
			fMap.put("sysFileName", sysFileName);
			
			// 5.메모리->실제 파일 업로드
			try {
				mf.transferTo(new File(realPath + sysFileName)); // 서버upload에 파일 저장
				//Map버전
				result= bDao.fileInsertMap(fMap);
				if(result==false) break;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.out.println("파일업로드 예외 발생");
				e.printStackTrace();
				result=false;
				break;  //반복 중단
			}
		} // For end
		return result;
	}
	// 파일 다운로드
	public ResponseEntity<Resource> fileDownload(BoardFile bfile, HttpSession session)
			throws IOException {
		log.info("fileDownload()");
		// 파일 저장 경로
		String realpath = session.getServletContext().getRealPath("/");
		realpath += "upload/" + bfile.getBf_sysname();
		// 실제 파일이 저장된 하드디스크까지 경로를 수립.
		InputStreamResource fResource = new InputStreamResource(new FileInputStream(realpath));

		// 파일명이 한글인 경우의 처리(UTF-8로 인코딩)
		String fileName = URLEncoder.encode(bfile.getBf_oriname(), "UTF-8");
		return ResponseEntity.ok()
				//다운로드 방식 
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
				//이미지 브라우저 열기
				//.contentType(MediaType.IMAGE_JPEG)
				//브라우저는 항상 서버로부터 최신 버전의 리소스를 다운로드
				.cacheControl(CacheControl.noCache()).body(fResource);
	}  //fileDownload end
	
	public void fileDelete(String[] sysfiles, HttpSession session) {
		String realPath = session.getServletContext().getRealPath("/");
		realPath += "upload/";
		log.info("delete realPath: {}",realPath);
		for (String sysname : sysfiles) {  
			File file = new File(realPath + sysname);
			log.info("++++AbsoluteFile: {}",file.getAbsoluteFile());
			if (file.exists()) {
				file.delete();
				log.info("서버 파일 삭제 완료");
			} else {
				log.info("이미 삭제된 파일");
			}
		}//for end
	}
}
