package com.project.notice_mybatis.domain;

import com.project.notice_mybatis.paging.Criteria;
import com.project.notice_mybatis.paging.PaginationInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommonDTO extends Criteria {

    /** 페이징 정보 */
    private PaginationInfo paginationInfo;

    /** 삭제 여부 */
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