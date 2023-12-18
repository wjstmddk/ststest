package com.icia.board.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.icia.board.dto.BoardDto;
import com.icia.board.dto.SearchDto;

import lombok.extern.slf4j.Slf4j;
@SpringBootTest
@Slf4j
class BoardDaoTest {
	@Autowired
	private BoardDao bDao;
	
	//@Test
	void compareQueryTest() {
		log.info(">>>>>{}",bDao.compareQuery());
	}
	//@Test
	void dnQueryTest() {
		SearchDto sDto=SearchDto.builder()
				.keyword("abc")
				.build();
		System.out.println("listCnt:"+sDto.getListCnt());
		log.info("#####:{}",bDao.dnQuery(sDto));
	}
	//@Test
	@DisplayName("forEachTest")
	void forEachTest() {
		Map<String, String> map=new HashMap<>();
		// map.put("w", "cha");  //작성자
		map.put("t", "1");  //제목
		//map.put("c", "무궁화"); //내용
		Map<String, Map<String,String>> outer=new HashMap<>();
		outer.put("map",map);
		List<BoardDto> list=bDao.forEachTest(outer);
		
		
		
		//List<Map<String, String>> map2=bDao.forEachTest(outer);
		log.info("+++++++list:{}",list);  //List반환시 ArrayList반환		
		log.info("+++++++List-className:{}"+list.getClass());
		//log.info("+++++++map2:{}",map2);  //Map반환시 HashMap반환
		//log.info("+++++++map2-className:{}"+map2.get(0).getClass());
	}
	@Test
	void insertDummyDataTest() {
		BoardDto bDto=new BoardDto();
		for(int i=0;i<35;i++) {
			bDto.setB_title("제목"+i).setB_contents("무궁화 꽃이 피었습니다.")
			.setB_writer("cha");
			bDao.insertDummyData(bDto);
		}
	}
}
