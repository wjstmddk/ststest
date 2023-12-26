package com.icia.board.test;
import static org.assertj.core.api.Assertions.assertThat;

//import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitTest {
	@DisplayName("1+2는 3이다") //테스트 이름
	//@Test //테스트 메소드
	public void junitTest() {
		int a=1;
		int b=2;
		int sum=3;          
		//(기대값과, 비교(실행)값)이 잘 구분되지 않는 Assertion 예
		Assertions.assertEquals(sum, a+b);  //성공
		//가독성이 좋은 Assertj 예
		assertThat(a+b).isEqualTo(sum); 
		//isNotEqualTo() 등등등이 있다.  
	}
	@DisplayName("10+20는 30이다") //테스트 이름
	@Test //테스트 메소드
	public void junitTest2() {
		int a=100;
		int b=20;
		int sum=30;
		Assertions.assertEquals(sum, a+b); //실패
	}
}
