package com.icia.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain=true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//Data Bean(dto,entity,vo)
//view(MemberDTO)--->controller-->service(DTO<--->Entity)--->DAO--->DB(table:MemberEntity)
public class MemberDto {
	private String m_id; //파라미터명==필드명==컬럼명
	private String m_pw;
	private String m_name;
	private String m_birth;   //DB: date(time)과 호환됨
	private String m_addr;
	private String m_phone;
	//minfo 뷰 공유
	private Integer m_point;  //회원포인트
	private String g_name;  //회원등급
}
