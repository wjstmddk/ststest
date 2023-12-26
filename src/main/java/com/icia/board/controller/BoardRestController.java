package com.icia.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.board.common.FileManager;
import com.icia.board.dto.BoardDto;
import com.icia.board.dto.BoardFile;
import com.icia.board.dto.MemberDto;
import com.icia.board.dto.ReplyDto;
import com.icia.board.service.BoardService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
@RestController
@Slf4j
public class BoardRestController {
	@Autowired
	private BoardService bSer;
	@Autowired
	private FileManager fm;
	
	@PostMapping("/board/reply3")
	public ReplyDto boardReply3(@RequestBody ReplyDto reply) {
		log.info("reply:{}",reply);
		ReplyDto rDto=bSer.replyInsert3(reply);
		return rDto;  //json
	}
	
	@PostMapping("/board/reply2")
	public Map<String, Object> replyInsert2(@RequestBody ReplyDto reply, HttpSession session){
		log.info("*****reply:{}",reply);
		List<ReplyDto> rList=bSer.replyInsert(reply);
//		if(rList) {
//			return "redirect:/reply/list?b_num"+reply.getR_bnum()"; 
//			bSer.getReplyList(reply.getR_bnum());
//		}
		BoardDto bDto=new BoardDto().setB_contents("test").setB_writer("cha");
		Map<String, Object> hMap=new HashMap<>();
		hMap.put("rList", rList);
		hMap.put("bDto", bDto);
		return hMap;
	}
	
	@PostMapping("/board/reply")
	public List<ReplyDto> replyInsert(ReplyDto reply, HttpSession session){
		//MemberDto member=(MemberDto)session.getAttribute("mb");
		//String id=member.getM_id();
		//reply.setR_writer(id);
		log.info("*****reply:{}",reply);
		List<ReplyDto> rList=bSer.replyInsert(reply);
		//ObjectMapper om=new ObjectMapper();
		//return om.writeValueAsString(rList);
		return  rList;  //자바객체--->jackson(메세지컨버터)--> json
	}
	@PostMapping("/board/delFile")
	// @ResponseBody
	public List<BoardFile> delFile(String sysname,Integer b_num,HttpSession session) {
		log.info("delFile sysname:{}",sysname);
		
		//fm.fileUpload에서는 DB업로드까지 하지만
		//fm.fileDelete에서는 DB삭제를 하지 않음, 일관성 없으니 조심
		if(bSer.delBoardFile(sysname)) {
			//String[] sysFiles= new String[]{sysname};
			String[] sysFiles= {sysname};  //배열초기화
			fm.fileDelete(sysFiles, session);
		}
		List<BoardFile> fList=bSer.getBoardFileList(b_num);
		if(fList.size()!=0) {  //파일이 없으면 size 0인 리스트반환 
			return fList;
		}
		return null;
	}
}
