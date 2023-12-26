package com.icia.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.icia.board.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberRestController {
	@Autowired
	private MemberService mSer;
	
	@GetMapping("/member/idCheck")
	//@ResponseBody
	public String idCheck(String m_id) {
		log.info("m_id:{}", m_id);
		String res=mSer.idCheck(m_id);
		return res;  //사용가능하면 'ok', 사용불가면 'fail'
	}
	
	
}
