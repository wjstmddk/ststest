package com.icia.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icia.board.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberRestController {

	@Autowired
	private MemberService mSer;
	
	@GetMapping("/member/idCheck")
	public String idCheck(String m_id) {
		log.info("m_id:{}",m_id);
		String res=mSer.idCheck(m_id);
		return res;
	}
}
