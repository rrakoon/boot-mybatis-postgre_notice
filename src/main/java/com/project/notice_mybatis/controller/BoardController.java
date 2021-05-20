package com.project.notice_mybatis.controller;

import com.project.notice_mybatis.constant.Method;
import com.project.notice_mybatis.domain.BoardDTO;
import com.project.notice_mybatis.service.BoardService;
import com.project.notice_mybatis.utils.UiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController extends UiUtil {

    @Autowired
    private BoardService boardService;

    //@RequestParam : view에서 전달받은 파라미터 처리하는데 사용.
    //value="값" = > view에서 받을 파라미터 값.
    //required = false => 지정 키값이 없을때 Bad Request Error발생 방지. default값은 true며 반드시 false로 고친다.
    //defaultValue = "값" => 지정 키값이 존재 안하면 기본값 할당.
    @GetMapping(value = "/write")
    public String openBoardWrite(@ModelAttribute("params") BoardDTO params,
            @RequestParam(value = "idx", required = false) Long idx, Model model) {

        if (idx == null) {
            model.addAttribute("board", new BoardDTO());
        } else {
            BoardDTO board = boardService.getBoardDetail(idx);
            if (board == null || "Y".equals(board.getDeleteYn())) {
                return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.",
                        "/board/list", Method.GET, null, model);
            }
            model.addAttribute("board", board);
        }
        return "board/write";
    }

    @PostMapping(value = "/register")
    public String registerBoard(@ModelAttribute("params") final BoardDTO params, final MultipartFile[] files, Model model) {
        Map<String,Object> pagingParams = getPagingParams(params);

        try {
            boolean isRegistered = boardService.registerBoard(params,files);
            if (isRegistered == false) {
                // TODO => 게시글 등록에 실패하였다는 메시지를 전달
                return showMessageWithRedirect("게시글 등록 실패",
                        "/board/list", Method.GET, pagingParams, model);
            }
        } catch (DataAccessException e) {
            // TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달
            return showMessageWithRedirect("DB 처리 과정 중 문제 발생",
                    "/board/list", Method.GET, pagingParams, model);

        } catch (Exception e) {
            // TODO => 시스템에 문제가 발생하였다는 메시지를 전달
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.",
                    "/board/list", Method.GET, pagingParams, model);
        }

        return showMessageWithRedirect("게시글 등록 완료",
                "/board/list", Method.GET, pagingParams, model);
    }

    @GetMapping("/list")
    public String openBoardList(@ModelAttribute("params") BoardDTO params, Model model) {
        //@ModelAttribute : 파라미터로 전달받은 객체를 자동으로 view로 전달.
        //1:1 매핑파라미터 => @ReqeustParam으로 넘겨받고 Model.addAttribute(key,value)로 view로 전달.
//        List<BoardDTO> boardDTOList = boardService.getBoardList(criteria);
        List<BoardDTO> boardDTOList = boardService.getBoardList(params);
        model.addAttribute("boardList", boardDTOList);

        return "/board/list";
    }

    @GetMapping("/view")
    public String openBoardDetail(@ModelAttribute("params") BoardDTO params,
                                  @RequestParam(value = "idx", required = false) Long idx, Model model) {
        if (idx == null) {
            // TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
            return showMessageWithRedirect("올바르지 않은 접근입니다.",
                    "/board/list", Method.GET, null, model);
        }

        BoardDTO board = boardService.getBoardDetail(idx);

        if (board == null || "Y".equals(board.getDeleteYn())) {
            // TODO => 없는 게시글이거나, 이미 삭제된 게시글이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
            return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.",
                    "/board/list", Method.GET, null, model);
        }
        model.addAttribute("board", board);
        boardService.viewCount(idx);

        return "board/view";
    }

    @PostMapping(value = "/delete")
    public String deleteBoard(@ModelAttribute("params") BoardDTO params,
                              @RequestParam(value = "idx", required = false) Long idx, Model model) {
        if (idx == null) {
            return showMessageWithRedirect("올바르지 않은 접근입니다.",
                    "/board/list", Method.GET, null, model);
        }

        Map<String, Object> pagingParams = getPagingParams(params);
        try {
            boolean isDeleted = boardService.deleteBoard(idx);
            if (isDeleted == false) {
                return showMessageWithRedirect("게시글 삭제에 실패하였습니다.",
                        "/board/list", Method.GET, pagingParams, model);
            }
        } catch (DataAccessException e) {
            return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.",
                    "/board/list", Method.GET, pagingParams, model);

        } catch (Exception e) {
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.",
                    "/board/list", Method.GET, pagingParams, model);
        }

        return showMessageWithRedirect("게시글 삭제가 완료되었습니다.",
                "/board/list", Method.GET, pagingParams, model);
    }

}
