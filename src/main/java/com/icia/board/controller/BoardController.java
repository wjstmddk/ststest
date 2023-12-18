package com.icia.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.board.dto.BoardDto;
import com.icia.board.dto.ReplyDto;
import com.icia.board.dto.SearchDto;
import com.icia.board.service.BoardService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {
	@Autowired
	private BoardService bSer;
	
	@GetMapping("/board/detail")
	public String boardDetail(Integer b_num, Model model) {
		if(b_num==null) {
			return "redirect:/board/list";
		}
		BoardDto board=bSer.getBoardDetail(b_num);
		List<ReplyDto> rList=bSer.getReplyList(b_num);
		//List<BoardFile> fList=bSer.getBoardFileList(b_num);
		//BoardDto board=bDao.getBoardDetail(b_num);  //처리 일관성 없음 
		if(board!=null) {
			model.addAttribute("board", board); 
			model.addAttribute("rList", rList);
			//model.addAttribute("fList",fList);
			//model.addAttribute("board", board);
			return "boardDetail";
		}else {
			return "redirect:/board/list";
		}
	}
	@GetMapping("/board/list")
	public String boardList(SearchDto sDto, Model model, HttpSession session) {
		log.info("before sDto:{}"+sDto);
		//기본값 설정
		if (sDto.getPageNum() == null) {sDto.setPageNum(1);}
		if (sDto.getListCnt() == null) {sDto.setListCnt(BoardService.LISTCNT);}
		if (sDto.getStartIdx() == null) {sDto.setStartIdx(0);}
		log.info("before sDto:{}"+sDto);
		List<BoardDto> bList=null;	
		//bList = bSer.getBoardList(pageNum);
		//정적 쿼리 작성시
//		if (sDto.getColname() == null && sDto.getColname()=="") {
//			bList = bSer.getBoardList(sDto.getPageNum());
//		} else {
//			bList = bSer.getBoardListSearch(sDto);
//		}
		//동적 쿼리 작성시
		//bList = bSer.getBoardListSearch(sDto);   //if 동적쿼리
		bList = bSer.getBoardListSearchNew(sDto);   //choose when 동적쿼리
		// log.info("====bList:{}",bList);
		// log.info("====bList size:{}개",bList.size());
		if (bList != null) {
			System.out.println("if sDto:"+sDto);
			String pageHtml = bSer.getPaging(sDto);
			//세션에 필요 정보 저장(pageNum, 검색관련 정보)
		    //페이지 번호 저장- 글쓰기 또는 상세보기 화면에서
			//목록으로 돌아갈 때 보고 있던 페이지로 돌아가기 위해
			//session.setAttribute("pageNum", sDto.getPageNum());
			if(sDto.getColname() != null) {
				session.setAttribute("sDto", sDto);  //페이지번호,컬럼이름,키워드
			}else {
				//검색이 아닐때는 SearchDto 제거
				session.removeAttribute("sDto");
			}
			// 1.서버 makeHtml 2.json=js제어 3.ArrayList==>jstl
			model.addAttribute("bList", bList); // jstl (쉽다, 협업X)
			model.addAttribute("paging", pageHtml);  
			// model.addAttribute("bList",
			// new ObjectMapper().writeValueAsString(bList)); //js(어렵다, 협업O)
			// model.addAttribute("bList", makeHtmlBlist(bList)); //서버
			return "boardList"; // jsp
		} else {
			return "redirect:/"; // index.jsp
		}
	}
	
}