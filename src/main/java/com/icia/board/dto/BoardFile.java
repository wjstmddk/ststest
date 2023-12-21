package com.icia.board.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import lombok.Data;

//@Entity  ////JPA
//Table기준: entity
//view기준: dto
@Data
public class BoardFile {
	//PK
	private int bf_num;
	private int bf_bnum; //FK
    private String bf_oriname; //  --a.jpg
    private String bf_sysname; //  --329840284093.jpg
	
}
