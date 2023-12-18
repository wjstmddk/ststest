package com.icia.board.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.icia.board.dto.MemberDto;

import lombok.RequiredArgsConstructor;
@SpringBootTest
class MemberDaoTest {
	@Autowired
	private  MemberDao mDao;
	
	//@Test
	@DisplayName("로그인 테스트")
	void loginTest() {
	
	}
	
	@Test
	@DisplayName("회원가입 테스트")
	@Transactional
	void joinTest() {
//		MemberDto mb = MemberDto.builder().m_id("aaa").m_pw("1111")
//				.m_name("에이").m_birth("20001010").m_addr("인천")
//				.m_phone("010-1111-1111").build();
		MemberDto mb = new MemberDto();
		mb.setM_id("ddd").setM_pw("1111").setM_name("에이").setM_birth("20001010")
		.setM_addr("인천").setM_phone("1111-1111");
		//boolean result = mDao.join(mb);
		//System.out.println("=====" + result);
		assertEquals(true,mDao.join(mb));
	}

}
