package com.icia.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;

import com.icia.board.dto.BoardDto;
import com.icia.board.dto.BoardFile;
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
	
	boolean boardWriteSelectKey(BoardDto board);
	boolean fileInsertMap(Map<String, String> fMap);
	BoardDto getBoardDetailWithFile(Integer b_num);
	
	@Delete("DELETE FROM REPLY WHERE R_BNUM=#{b_num}")
	boolean replyDelete(Integer b_num);
	@Select("SELECT BF_SYSNAME FROM BOARDFILE WHERE BF_BNUM=#{b_num}")
	String[] getSysNameFiles(Integer b_num);
	
	@Delete("DELETE FROM BOARDFILE WHERE BF_BNUM=#{b_num}")
	boolean boardFileDelete(Integer b_num);
	@Delete("DELETE FROM BOARD WHERE B_NUM=#{b_num}")
	boolean boardDelete(Integer b_num);
	@Delete("DELETE FROM BOARDFILE WHERE BF_SYSNAME=#{sysname}")
	boolean delBoardFile(String sysname);
	
	@Select("select bf_oriname, bf_sysname from boardfile where bf_bnum=#{b_num}")
	List<BoardFile> getBoardFileList(Integer b_num);
	
	@Update("UPDATE board SET b_title=#{b_title},b_contents=#{b_contents}, b_date=now()"
			+ " WHERE b_num=#{b_num}")
	boolean boardUpdate(BoardDto board);
}
