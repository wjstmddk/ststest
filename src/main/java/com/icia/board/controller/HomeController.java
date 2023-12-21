package com.icia.board.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.icia.board.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	@Autowired
	private MemberService mSer;
	
	@GetMapping("/")
	public String home(Model model,HttpSession session) {
		//사용즉시 삭제하기 힘들면 루트에서 일괄 삭제할 것.
		//로그인 이전 url
		session.removeAttribute("urlPrior_login");  
		//검색정보(컬럼,키워드)-board/list에서 삭제함.
		session.removeAttribute("sDto");
		//글쓰기, 글상세보기 이전 페이지정보
		session.removeAttribute("pageNum");
		return "index"; 
	}


}
