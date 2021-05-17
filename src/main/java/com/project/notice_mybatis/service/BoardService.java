package com.project.notice_mybatis.service;

import com.project.notice_mybatis.domain.BoardDTO;
import com.project.notice_mybatis.paging.Criteria;

import java.util.List;

public interface BoardService {
     
     boolean registerBoard(BoardDTO params);

     BoardDTO getBoardDetail(Long idx);

     boolean deleteBoard(Long idx);

     List<BoardDTO> getBoardList(Criteria criteria);
}
