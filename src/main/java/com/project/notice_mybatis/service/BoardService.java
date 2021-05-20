package com.project.notice_mybatis.service;

import com.project.notice_mybatis.domain.AttachDTO;
import com.project.notice_mybatis.domain.BoardDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

     boolean registerBoard(BoardDTO params);

     boolean registerBoard(BoardDTO params, MultipartFile[] files);

     BoardDTO getBoardDetail(Long idx);

     boolean deleteBoard(Long idx);

//     List<BoardDTO> getBoardList(Criteria criteria);
     List<BoardDTO> getBoardList(BoardDTO params);

     boolean viewCount(Long idx);

     List<AttachDTO> getAttachFileList(Long boardIdx);

     AttachDTO getAttachDetail(Long idx);

}
