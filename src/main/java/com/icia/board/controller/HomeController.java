package com.icia.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icia.board.dto.ReplyDto;
import com.icia.board.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	@Autowired
	private MemberService mSer;

	@GetMapping("/")
	public String home(Model model, HttpSession session) {
		// 사용즉시 삭제하기 힘들면 루트에서 일괄 삭제할 것.
		// 로그인 이전 url
		session.removeAttribute("urlPrior_login");
		// 검색정보(컬럼,키워드)-board/list에서 삭제함.
		session.removeAttribute("sDto");
		// 글쓰기, 글상세보기 이전 페이지정보
		session.removeAttribute("pageNum");
		return "index";
	}

	@GetMapping("/test")
	public String test() {
		return "test";  //jsp
	}
	
	// PathVariable: {url파라미터명}과 일치해야 함.
	//url:get(select): /member/1    , product/summer/123
	//url:post(insert, delete,update)
	//url:delete  member/1
	//url:Put, Patch(update), 
	@GetMapping("/test/{id}/{pw}")
				//, consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				//, consumes= MediaType.TEXT_PLAIN_VALUE
				//, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> testParam2(@PathVariable String id, @PathVariable("pw") Integer pw)
	{
		log.info("----id:{}, pw:{}", id, pw);
		Map<String,Object> map=new HashMap<String, Object>();
		map=mSer.testParam2(id, pw);
		if (map!=null){
			map.put("msg","로그인 성공");
			
			return map; 
		}
		map.put("msg","로그인 실패");
		return map;
	}
	@PostMapping("/test/time")
  	@ResponseBody
  	public ReplyDto time(ReplyDto reply) {
  		log.info("reply:{}",reply);
  		//DB insert는 여러분이 해볼것 DateLocalTime , 오라클 Date, Timestamp과 호환
  		return  reply;  //json-->@JsonFormat
  	}
  		

}
