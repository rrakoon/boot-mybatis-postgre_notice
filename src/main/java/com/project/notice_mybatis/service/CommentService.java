package com.project.notice_mybatis.service;

import com.project.notice_mybatis.domain.CommentDTO;

import java.util.List;

public interface CommentService {

    public boolean registerComment(CommentDTO params);

    public boolean deleteComment(Long idx);

    public List<CommentDTO> getCommentList(CommentDTO params);

}
