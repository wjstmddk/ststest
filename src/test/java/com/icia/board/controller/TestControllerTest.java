package com.icia.board.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.icia.board.dao.MemberDao;
import com.icia.board.dto.MemberDto;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootTest  //테스트용 애플리케이션 컨텍스트(IoC컨테이너) 생성
@AutoConfigureMockMvc //테스트용MockMvc를 생성하여 요청응답 기능 제공
public class TestControllerTest {
	@Autowired
    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
     
    @Autowired
    private MemberDao mDao;
    
	//@BeforeAll  //junit5 모든 @Test 전에 1번만 static 메소드를 실행함
	@BeforeEach  //junit5 모든 @Test 전에 매번 실행
	public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                  .build();
    }
//	@AfterEach  //junit5 모든 @Test 후에 매번 실행
//	public void cleanUp() {
//		mDao.deleteAllMember(); 
//	}
	@DisplayName("getAllMembers: 회원리스트 조회에 성공한다.")
    @Test
    @Transactional  //test후 롤백처리한다. 따라서 위 cleanUp메소드는 필요없다.
    public void getAllMembers() throws Exception {
        // given
        final String url = "/testJunit";
        MemberDto member=MemberDto.builder().m_id("aaa").m_pw("1111").m_name("에이").build();
        //MemberDto member=new MemberDto("aaa","1111","에이");
          
        boolean joinResult = mDao.joinTest(member);
        assertThat(joinResult).isEqualTo(true);
        // when
        final ResultActions result = mockMvc.perform(get(url) // 1
                .accept(MediaType.APPLICATION_JSON)); // 2
       
        // then
        result.andExpect(status().isOk())
              .andExpect(jsonPath("$[0].m_id").value(member.getM_id()))
              .andExpect(jsonPath("$[0].m_name").value(member.getM_name()));
        }
}