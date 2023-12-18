package com.icia.board.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReplyDto {
	private int r_num;  //pk
	private int r_bnum; //fk(원글 번호)
	private String r_contents; //댓글 내용
	private String r_writer;   //작성자id
	//댓글 데이터는 restController에서 jackson이 json 변환함
    //DTO에서 날짜 형식을 지정해야 함.
	
	//1.날짜 형식을 Json으로 주고받을때,
    //json과 Timestamp서로 호환되지 않음, 설정필수
	@JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	//2.Json이 아닌 날짜형식으로 주고받을때,
	//레거시일때 intput type="datetime-local"를 날짜데이터를 넘기면 꼭 'T'추가해서 설정해야함
	//boot3.0이상은  @DateTimeFormat 생략가능
	//@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:SS")  
    private LocalDateTime r_date;  //DB의 Date(Time),TimeStamp형과 호환됨 다만 jstl로 표현시 별도 설정필요
    //private String r_date;  //mysql: date_format(r_date,'%y-%m-%d %h:%i:%s %p') 
}
