package com.icia.board.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysql.cj.log.Log;

import lombok.extern.slf4j.Slf4j;
@SpringBootTest
@Slf4j
class BoardServiceTest {
	@Autowired
	BoardService bSer;
	@Test
	void createQueryTest() {
//		log.info("*****List:{}",bSer.createQuery());
	}

}
