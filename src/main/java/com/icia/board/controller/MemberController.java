package com.icia.board.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.icia.board.dao.MemberDao;
import com.icia.board.dto.MemberDto;
import com.icia.board.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
//@RequiredArgsConstructor
//@RequestMapping("/member")
public class MemberController {
	@Autowired
	//@NonNull //final
	private MemberService mSer;
	
	@GetMapping("/member/loginfrm")
	public String login(HttpSession session) {
		//인터셉터에서 제외url 로그인 체크
//		if(session.getAttribute("mb")!=null) {
//			return "redirect:/";
//		}
		return "login";  //login.jsp
	}
	@PostMapping("/member/login")
	//public String login(@RequestParam String m_id, 
	//					@RequestParam("m_pw") String pw) {
	//MemberDto가 없을때는 HashMap으로
	public String login(@RequestParam HashMap<String,String> member, 
			Model model,HttpSession session, RedirectAttributes rttr) {
		MemberDto mb=mSer.login(member);
		log.info("=====mb:{}",mb);
		if(mb!=null) {
			session.setAttribute("mb", mb);  //로그인 성공후 회원정보를 출력위해
			//로그인후 이전url로 요청하기
			Object url=session.getAttribute("urlPrior_login");
			System.out.println("==이전URL:"+url);
			if(url!=null) {
				session.removeAttribute("urlPrior_login");
				// redirect: /board/list?pageNum=2
				return "redirect:"+url.toString(); 
			}
			return "redirect:/board/list?pageNum=1";  //pageNum=1 생략가능
		}else {
			rttr.addFlashAttribute("msg", "로그인 실패");
			return "redirect:/member/loginfrm";
		}
	}
	
	@GetMapping("/member/joinfrm")
	public String join(HttpSession session) {
		log.info("=====회원가입 양식===");
		//인터셉터에서 제외url 로그인 체크
//		if(session.getAttribute("mb")!=null) {
//			return "redirect:/";
//		}
		return "join"; //join.jsp
	}
	@PostMapping("/member/join")
	public String join(MemberDto member,Model model, RedirectAttributes rttr) {
		log.info("========member:{}",member);  //toString()
		boolean result=mSer.join(member);
		if(result) {
			//model.addAttribute("msg", "가입성공");
			//session저장->새요청의 Model(request영역)에 저장-->session삭제 // 단, 1회성 data 
			rttr.addFlashAttribute("msg", "가입성공"); 
			//rttr.addAttribute("msg", "가입성공"); //request객체 //F5시 여러번 사용
			return "redirect:/"; //index.jsp  redirect:/url (get만 요청가능) 
		}else {
			model.addAttribute("msg", "가입실패");
			return "join";
		}
	}//join End
	@PostMapping("/member/logout")
	public String postLogout(HttpSession session, RedirectAttributes rttr) {
		log.info("post 로그아웃");
		session.invalidate();
		rttr.addFlashAttribute("msg", "Post로그아웃 성공");
		return "redirect:/";
	}
}//class End
