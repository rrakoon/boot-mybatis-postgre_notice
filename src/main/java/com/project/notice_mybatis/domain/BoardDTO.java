package com.project.notice_mybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BoardDTO {
    /* 번호 (PK) */
    private Long idx;

    /* 제목 */
    private String title;

    /* 내용 */
    private String content;

    /* 작성자 */
    private String writer;

    /* 조회 수 */
    private int viewCnt;

    /* 공지 여부 */
    private String noticeYn;

    /* 비밀 여부 */
    private String secretYn;

    /* 삭제 여부 */
    private String deleteYn;



    /*
     postgresql timestamp with time zone issue
     LocalDateTime => LcalDateTimeTypeHandler 변경 mybatis 3.5이상시 정상작동.
     */
    /* 등록일 */
    private LocalDateTime insertTime;

    /* 수정일 */
    private LocalDateTime updateTime;

    /* 삭제일 */
    private LocalDateTime deleteTime;

}
