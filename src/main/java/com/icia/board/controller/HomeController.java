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
		//이전 사용했던 세션 삭제
		session.removeAttribute("urlPrior_login");
		session.removeAttribute("sDto");
		return "index"; 
	}


}
