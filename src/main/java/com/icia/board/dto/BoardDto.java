package com.icia.board.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
	// 컨트롤러에서 @RequestPart로 받아도 됨,
	// 하지만 필드로 추가하면 파라미터가 줄어서 좋다.
	List<MultipartFile> attachments;  
	//글상세보기할때 해당글에 첨부된 파일리스트
	private List<BoardFile> bfList;  
	//private List<ReplyDto> rList;  
}
