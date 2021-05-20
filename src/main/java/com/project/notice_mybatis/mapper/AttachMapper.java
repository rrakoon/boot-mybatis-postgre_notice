package com.project.notice_mybatis.mapper;

import com.project.notice_mybatis.domain.AttachDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttachMapper {
    int insertAttach(List<AttachDTO> attachList);

    AttachDTO selectAttachDetail(Long idx);

    int deleteAttach(Long boardIdx);

    List<AttachDTO> selectAttachList(Long boardIdx);

    int selectAttachTotalCount(Long boardIdx);
}
