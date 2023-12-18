package com.icia.board.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.icia.board.dto.MemberDto;

@Mapper
public interface MemberDao {
	
	boolean login(HashMap<String, String> hMap);

	boolean join(MemberDto member);
	//@Select("select")
	String getSecurityPw(String id);

	MemberDto getMemberInfo(String id);

	boolean idCheck(String m_id);
}

