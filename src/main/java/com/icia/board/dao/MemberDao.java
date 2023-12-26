package com.icia.board.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.icia.board.dto.MemberDto;

@Mapper
public interface MemberDao {
	
	boolean login(HashMap<String, String> hMap);

	boolean join(MemberDto member);
	//@Select("select")
	String getSecurityPw(String id);

	MemberDto getMemberInfo(String id);
	@Select("select count(*) from member where m_id=#{m_id}")
	boolean idCheck(String m_id);

	void updateMemberPoint(MemberDto member);

	//Map으로 반환시 key는 대문자 컬럼명, value는 컬럼값이다. 
	@Select("select *  from member where m_id=#{param1} and m_pw=#{param2}")
	Map<String, Object> testParam2(String id, Integer pw);
	
	// junit
	@Insert("insert into testMember values(#{m_id},#{m_pw},#{m_name})")
	boolean joinTest(MemberDto member);
}

