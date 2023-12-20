package com.icia.board.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.icia.board.dao.MemberDao;
import com.icia.board.dto.MemberDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
	@Autowired
	private MemberDao mDao;
	//레포지토리-
	// 비번이 암호화된 회원만 로그인가능할것.
	public MemberDto login(HashMap<String, String> member) {
		// 복호화는 안되지만 비교 가능--->cha-->432pi3p45328095-403
		String encoPwd = mDao.getSecurityPw(member.get("m_id"));//레포지토리
		log.info("====encoPwd:{}", encoPwd);
		BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		//레포지토리
		if (encoPwd != null) {
			log.info("====아이디 존재함");
			if (pwEncoder.matches(member.get("m_pw"), encoPwd)) {
				log.info("====로그인 성공");
				return mDao.getMemberInfo(member.get("m_id"));//레포지토리결과값
			} else {
				log.info("====비번 오류");
				return null;
			}
		} else {
			log.info("====아이디 오류");
			return null;
		}
		//controller
	}

	public boolean join(MemberDto member) {
		// Encoder(암호화)<------->Decoder(복호화)
		BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		member.setM_pw(pwEncoder.encode(member.getM_pw()));
		return mDao.join(member);
	}

	public String idCheck(String m_id) {
	   if(mDao.idCheck(m_id)==false) {
			return "ok"; // 사용가능한 아이디
		}
		return "fail";
	}
}
