package com.project.notice_mybatis.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO extends CommonDTO {
    //댓글 번호(PK)
    private Long idx;

    //게시글 번호 (FK)
    private Long boardIdx;

    //댓글내용
    private String content;

    //댓글 작성자.
    private String writer;

}