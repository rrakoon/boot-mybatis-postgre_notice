package com.project.notice_mybatis.mapper;

import com.project.notice_mybatis.domain.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


//DAO클래스에 @Repository선언으로 DB와 통신하는 클래스를 나타냄
//Mybatis에서 @Mapper 지정시 XML Mapper와 이름이 같은 SQL문 실행.
@Mapper
public interface BoardMapper {

    //게시글 생성
    int insertBoard(BoardDTO params);
    //게시글 조회
    BoardDTO selectBoardDetail(Long idx);
    //게시글 수정
    int updateBoard(BoardDTO params);
    //게시글 삭제
    int deleteBoard(Long idx);
    //게시글 목록
    List<BoardDTO> selectBoardList();
    //게시글 갯수
    int selectBoardTotalCount();

}


