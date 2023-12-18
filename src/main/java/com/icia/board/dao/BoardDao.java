package com.icia.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import com.icia.board.dto.BoardDto;
import com.icia.board.dto.ReplyDto;
import com.icia.board.dto.SearchDto;

@Mapper
public interface BoardDao {
	@Insert("INSERT INTO BOARD VALUES(NULL, #{b_title},#{b_contents},#{b_writer},DEFAULT,DEFAULT)")
	int insertDummyData(BoardDto bDto);
	List<BoardDto> getBoardList(Map<String, Integer> pageMap);
	List<BoardDto> getBoardListSearch(SearchDto sDto);
	
	
	List<BoardDto> getBoardListSearchNew(SearchDto sDto);
	List<BoardDto> dnQuery(SearchDto sDto);
	//@Select("select * from blist where b_num < 10")
	List<BoardDto> compareQuery();
	List<BoardDto> forEachTest(Map<String, Map<String,String>> map);
	//List<Map<String,String>> forEachTest(Map<String, Map<String,String>> map);
	
	@Select("select count(*) from board")
	int getBoardCount(SearchDto sDto);
	BoardDto getBoardDetail(Integer b_num);
		
	boolean replyInsert(ReplyDto reply);
	List<ReplyDto> getReplyList(Integer b_num);
	
	boolean replyInsertSelectKey(ReplyDto reply);
	ReplyDto getReplyNewOne(int r_num);
	
}
