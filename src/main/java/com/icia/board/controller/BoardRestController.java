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
import com.icia.board.dto.BoardDto;
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

	@PostMapping("/board/reply")
	public List<ReplyDto> replyInsert(Integer r_bnum, ReplyDto reply, HttpSession session) {
		// MemberDto member=(MemberDto)session.getAttribute("mb");
		// String id=member.getM_id();
		// reply.setR_writer(id);

		log.info("*****reply:{}", reply);
		List<ReplyDto> rList = bSer.replyInsert(reply);
		BoardDto board = bSer.getBoardDetail(r_bnum);
		System.out.println("b_num"+r_bnum);
		List<ReplyDto> nList = bSer.getReplyList(r_bnum);
		// ObjectMapper om=new ObjectMapper();
		// return om.writeValueAsString(rList);
		return rList; // 자바객체--->jackson(메세지컨버터)--> json

	}
	@PostMapping("/board/reply3")
	public ReplyDto boardReply3(@RequestBody ReplyDto reply) {
		log.info("reply:{}",reply);
		ReplyDto rDto=bSer.replyInsert3(reply);
		return rDto;
	}
	@PostMapping("/board/reply2")
	public Map<String, Object> replyInsert2(ReplyDto reply, HttpSession session){
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
}
