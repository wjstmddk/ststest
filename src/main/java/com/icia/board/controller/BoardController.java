package com.icia.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.board.dto.BoardDto;
import com.icia.board.dto.BoardFile;
import com.icia.board.dto.MemberDto;
import com.icia.board.dto.ReplyDto;
import com.icia.board.dto.SearchDto;
import com.icia.board.exception.DBException;
import com.icia.board.service.BoardService;
import com.icia.board.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
//@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService bSer;
	
	@GetMapping("/board/delete")
	public String boardDelete(Integer b_num, HttpSession session, 
			RedirectAttributes rttr) {
		log.info("boardDelete b_num:{}",b_num);
		try {
			bSer.boardDelete(b_num, session);
			rttr.addFlashAttribute("msg", b_num+"번 삭제성공");  //request영역에 1번 사용후 삭제됨 
			//rttr.addAttribute("msg", "삭제성공");  //request객체에 파라미터 저장 
			return "redirect:/board/list?pageNum=1";
		}catch(DBException e){
			log.info(e.getMessage());
			rttr.addFlashAttribute("msg", b_num+"번 삭제실패");  //request영역에 1번 사용후 삭제됨 
			return "redirect:/board/detail?b_num="+b_num;
		}
	}
	@GetMapping("/board/write")
	public String boardWrite() {
		log.info("글쓰기 창 열기");
		return "boardWrite";  //.jsp
	}
	
	@PostMapping("/board/write")
	public String boardWrite(BoardDto board, RedirectAttributes rttr, HttpSession session) {
    //public String boardWrite(BoardDto board, @RequestPart List<MultipartFile> attachments
	//		                 ,HttpSession session, RedirectAttributes rttr) {
		log.info("글쓰기 처리");
		log.info("board={}", board);
        //(주의)file미첨부시: 파라미터는 attachments=""(빈문자열 넘어옴)
		for(MultipartFile mf: board.getAttachments()) {
			log.info("파일명:{}",mf.getOriginalFilename());
			log.info("파일 사이즈:{}byte", mf.getSize());
			log.info("---------------------");
		}
		log.info("첨부파일 개수:{}개",board.getAttachments().size());
		log.info("첨부파일 없니?:{}",board.getAttachments().get(0).isEmpty());
		
		//첨부파일 없을 때 session 불필요
		//boolean result = bSer.boardWrite(board);

		//session: 파일업로드 프로젝트 경로 얻기, 로그인한 회원정보
		boolean result = bSer.boardWrite(board,session);
		if (result) {
			rttr.addFlashAttribute("msg", "글쓰기 성공");
			return "redirect:/board/list";  
////			// redirect는 get요청만 가능 
		}else { //forward는 get--->get, post--->post만 가능
			rttr.addFlashAttribute("msg", "글쓰기 실패");
			return "redirect:/board/write";
		}
	}
	@GetMapping("/board/detail")
	public String boardDetail(Integer b_num, Model model) {
		if(b_num==null) {
			return "redirect:/board/list";
		}
		BoardDto board=bSer.getBoardDetail(b_num);//첨부된 파일들, 대글들과 함께
		//List<BoardFile> bfList=bSer.getBoardFileList(b_num);
		log.info("^^^^^board:{}", board);
		log.info("^^^^^bf:{}", board.getBfList());
		if(board!=null) {
			List<ReplyDto> rList=bSer.getReplyList(b_num);
			model.addAttribute("board", board);   //with 첨부파일
			//model.addAttribute("bfList",bfList);
			model.addAttribute("rList", rList);
			return "boardDetail";
		}else {
			return "redirect:/board/list";
		}
	}
	@GetMapping("/board/download")
	public ResponseEntity<Resource> fileDownload(BoardFile bfile,HttpSession session) 
			throws IOException{
		log.info("fileDownload()");
		ResponseEntity<Resource> resp=bSer.fileDownload(bfile,session);
		return resp;
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
			session.setAttribute("pageNum", sDto.getPageNum());
			if(sDto.getColname() != null) {
				session.setAttribute("sDto", sDto);  //페이지번호,컬럼이름,키워드
				log.info("%%%검색 컬럼존재시 sDto 세션에 저장");
			}else {
				//검색이 아닐때는 SearchDto 제거
				log.info("%%%검색 컬럼없으면 sDto 세션 삭제");
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
	@GetMapping("/board/update")
	public String boardUpdateFrm(Integer b_num, Model model) {
		log.info("글 수정창 열기");
		BoardDto board=bSer.getBoardDetail(b_num);
		//List<BoardFile> fList=bSer.getBoardFileList(b_num);
		if(board!=null) {
			model.addAttribute("board", board);
			//model.addAttribute("fList", fList);
			return "boardUpdate";
		}else {
			return "redirect:/board/list";
		}
	}
	@PostMapping("/board/update")
    //public String boardUpdate(@RequestPart List<MultipartFile> attachments,BoardDto board
    //							,HttpSession session,RedirectAttributes rttr){
	public String boardUpdate(BoardDto board,HttpSession session,RedirectAttributes rttr){
        log.info("글 수정 처리");
        log.info("board: {}",board);
        log.info("files size: {}",board.getAttachments().size());
        //수정시 포인트 증가는 없으므로 boardWrite재활용 불가
        boolean result=bSer.boardUpdate(board,session);
        if(result) {
			rttr.addFlashAttribute("msg", "수정 성공");
			return "redirect:/board/detail?b_num="+board.getB_num();
		}else {
			rttr.addFlashAttribute("msg", "수정 실패");
			return "redirect:/board/update?b_num="+board.getB_num();
		}
    }
}//end class