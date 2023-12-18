package com.icia.board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  //java config ---> 레거시 pro에서는 xml로 한다.
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	SessionInterceptor interceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
		//컨트롤러에서 처리할 수 없는 매핑url까지 인터셉터
		.addPathPatterns("/**")    //"/board/list/1"  
		.excludePathPatterns("/","/js/**","/css/**","/img/**")  
		.excludePathPatterns("/member/loginfrm","/member/login","/member/logout")  
		.excludePathPatterns("/member/joinfrm","/member/join")
		//서버 에러시 자동으로 /error 요청함 
		.excludePathPatterns("/favicon.ico","/error");
		//.excludePathPatterns("/board/list");  
	}
}
