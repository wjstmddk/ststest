package com.icia.board.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
	private int b_num;
	private String b_title; 
	private String b_contents; 
	private String b_writer;   //
	private String m_name;  //작성자이름 
	//private String b_date;  //select date_format(b_date,'%y-%m-%d %h:%i:%s %p') as b_date,...
	private LocalDateTime b_date;   //jdk1.8추가, 변환이 쉽다.   
	//private Timestamp b_date;     //old, 날짜시간
	private int b_views;
	
}
