package com.project.notice_mybatis.mapper;

import com.project.notice_mybatis.domain.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    
    //댓글 등록
    public int insertComment(CommentDTO params);
    
    //댓글 정보
    public CommentDTO selectCommentDetail(Long idx);

    //댓글 수정
    public int updateComment(CommentDTO params);

    //댓글 삭제
    public int deleteComment(Long idx);

    //댓글 목록
    public List<CommentDTO> selectCommentList(CommentDTO params);

    //댓글 갯수
    public int selectCommentTotalCount(CommentDTO params);
    
}
