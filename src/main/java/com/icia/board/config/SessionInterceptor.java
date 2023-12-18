package com.icia.board.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Component  
@Slf4j
public class SessionInterceptor implements AsyncHandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//http://localhost/member/login?id=aaa&pw=1111
		//log.info("===preHandle call uri={}",request.getRequestURI());
		//log.info("===queryString={}",request.getQueryString());
		HttpSession session=request.getSession();
		if(session.getAttribute("mb")==null) {
			//로그인 성공후 이전url 요청을 처리하기 위해 저장 
			session.setAttribute("urlPrior_login", request.getRequestURI()
			+"?"+request.getQueryString());
			log.info("=====인터셉트!--로그인 안함");
			response.sendRedirect("/member/loginfrm");  //login.jsp
			return false;   //컨트롤러 핸들러 진행 중단
		}
		return true;  //핸들러 진행(컨트롤러 진입)
	}

//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		// TODO Auto-generated method stub
//		System.out.println("view직전 호출");
//	}
}
