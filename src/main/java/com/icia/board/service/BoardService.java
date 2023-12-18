package com.icia.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icia.board.common.Paging;
import com.icia.board.dao.BoardDao;
import com.icia.board.dto.BoardDto;
import com.icia.board.dto.ReplyDto;
import com.icia.board.dto.SearchDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {
	@Autowired
	private BoardDao bDao;
	public static final int LISTCNT=10;
	public static final int PAGECOUNT=2;
	
	public List<BoardDto> getBoardList(Integer pageNum) {
		//페이지번호를 limit시작 번호로 변경
		int startIdx=(pageNum-1)*LISTCNT;
		Map<String,Integer> pageMap=new HashMap<>();
		pageMap.put("listCnt", LISTCNT);
		pageMap.put("startIdx", startIdx);
		List<BoardDto> bList=bDao.getBoardList(pageMap);
		return bList;
	}
	public List<BoardDto> getBoardListSearch(SearchDto sDto) {
		log.info("=====sDto:" + sDto); // 1
		//SQL 쿼리문의 limit 부분 설정.
        int pNum = sDto.getPageNum();
        //페이지번호를 limit 시작 번호로 변경- 1P:idx(0)~ , 2P:idx(10)~
        sDto.setStartIdx((pNum - 1) * sDto.getListCnt());
		List<BoardDto> bList=bDao.getBoardListSearch(sDto);
		return bList;
	}
	public List<BoardDto> getBoardListSearchNew(SearchDto sDto) {
		log.info("#### sDto:" + sDto); // 1
		//SQL 쿼리문의 limit 부분 설정.
        int pNum = sDto.getPageNum();
        //페이지번호를 limit 시작 번호로 변경- 1P:idx(0)~ , 2P:idx(10)~
        sDto.setStartIdx((pNum - 1) * sDto.getListCnt());
		List<BoardDto> bList=bDao.getBoardListSearchNew(sDto); 
		return bList;
	}
	public String getPaging(SearchDto sDto) {
		int totalNum=bDao.getBoardCount(sDto); //전체 글의 갯수, 키워드 있거나 없거나
		log.info("====totalNum:{}",totalNum);
		//int listCount =10; //필드에 정의함, 페이지당 글의 갯수
		//int pageCount =2;  //필드에 정의함 
		//String boardName="/board/list";
		String listUrl=null;
		if(sDto.getColname() !=null) {
			listUrl="/board/list?colname="+sDto.getColname()
			        + "&keyword="+sDto.getKeyword()+"&";
			// /board/list?colname=b_title&keyword=3&
		}else {
			listUrl="/board/list?";
		}
		Paging paging=new Paging(totalNum, sDto.getPageNum(), sDto.getListCnt(), PAGECOUNT, listUrl);
		return paging.makeHtmlPaging();
	}
	public BoardDto getBoardDetail(Integer b_num) {
		//BL(업무 로직)
		return bDao.getBoardDetail(b_num);
	}
	public List<ReplyDto> getReplyList(Integer b_num) {
		return bDao.getReplyList(b_num);
	}
	public List<ReplyDto> replyInsert(ReplyDto reply) {
		List<ReplyDto> rList=null;
		if(bDao.replyInsert(reply)) {
			rList=bDao.getReplyList(reply.getR_bnum());
		}
		return rList;
	}
	public ReplyDto replyInsert3(ReplyDto reply) {
		ReplyDto rDto=null;
		if(bDao.replyInsert(reply)) {
			int r_num=bDao.getReplyR_Num();
			log.info("****r_num:{}",reply.getR_num());
			rDto=bDao.getReplyNewOne(reply.getR_num());
		}
		return null;
	}
}
