package com.project.notice_mybatis.service;

import com.project.notice_mybatis.domain.AttachDTO;
import com.project.notice_mybatis.domain.BoardDTO;
import com.project.notice_mybatis.mapper.AttachMapper;
import com.project.notice_mybatis.mapper.BoardMapper;
import com.project.notice_mybatis.paging.PaginationInfo;
import com.project.notice_mybatis.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private AttachMapper attachMapper;

    @Autowired
    private FileUtil fileUtil;

    @Override
    public boolean registerBoard(BoardDTO params) {
        int queryResult = 0;

        if (params.getIdx() == null) {
            queryResult = boardMapper.insertBoard(params);
        } else {
            queryResult = boardMapper.updateBoard(params);
            // 파일이 추가, 삭제, 변경된 경우
            if ("Y".equals(params.getChangeYn())) {
                attachMapper.deleteAttach(params.getIdx());

                // fileIdxs에 포함된 idx를 가지는 파일의 삭제여부를 'N'으로 업데이트
                if (CollectionUtils.isEmpty(params.getFileIdxs()) == false) {
                    attachMapper.undeleteAttach(params.getFileIdxs());
                }
            }
        }

        return (queryResult > 0);
    }

    @Override
    public boolean registerBoard(BoardDTO params, MultipartFile[] files) {
        int queryResult = 1;

        if (registerBoard(params) == false) {
            return false;
        }

        List<AttachDTO> fileList = fileUtil.uploadFiles(files, params.getIdx());
        if (CollectionUtils.isEmpty(fileList) == false) {
            queryResult = attachMapper.insertAttach(fileList);
            if (queryResult < 1) {
                queryResult = 0;
            }
        }

        return (queryResult > 0);
    }

    @Override
    public BoardDTO getBoardDetail(Long idx) {
        return boardMapper.selectBoardDetail(idx);
    }

    @Override
    public boolean deleteBoard(Long idx) {
        int queryResult = 0;
        BoardDTO board = boardMapper.selectBoardDetail(idx);
        if (board != null && "N".equals(board.getDeleteYn())) {
            queryResult = boardMapper.deleteBoard(idx);
        }
        return (queryResult == 1) ? true : false;
    }

    @Override
    public List<BoardDTO> getBoardList(BoardDTO params) {
        List<BoardDTO> boardList = Collections.emptyList();

//        int boardTotalCount = boardMapper.selectBoardTotalCount(criteria);
        int boardTotalCount = boardMapper.selectBoardTotalCount(params);

        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(boardTotalCount);

        params.setPaginationInfo(paginationInfo);

        if (boardTotalCount > 0) {
//            boardList = boardMapper.selectBoardList(criteria);
            boardList = boardMapper.selectBoardList(params);
        }

        return boardList;
    }

    @Override
    public boolean viewCount(Long idx) {
        return boardMapper.viewCount(idx);
    }

    @Override
    public List<AttachDTO> getAttachFileList(Long boardIdx) {

        int fileTotalCount = attachMapper.selectAttachTotalCount(boardIdx);
        if (fileTotalCount < 1) {
            return Collections.emptyList();
        }
        return attachMapper.selectAttachList(boardIdx);
    }


}
