package com.project.notice_mybatis.controller;

import com.project.notice_mybatis.constant.Method;
import com.project.notice_mybatis.domain.BoardDTO;
import com.project.notice_mybatis.service.BoardService;
import com.project.notice_mybatis.util.UiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model) {
        if (idx == null) {
            model.addAttribute("board", new BoardDTO());
        } else {
            BoardDTO board = boardService.getBoardDetail(idx);
            if (board == null) {
                return "redirect:/board/list";
            }
            model.addAttribute("board", board);
        }
        return "board/write";
    }

    @PostMapping(value = "/register")
    public String registerBoard(final BoardDTO params, Model model) {
        try {
            boolean isRegistered = boardService.registerBoard(params);
            if (isRegistered == false) {
                // TODO => 게시글 등록에 실패하였다는 메시지를 전달
                return showMessageWithRedirect("게시글 등록 실패", "/board/list", Method.GET, null, model);
            }
        } catch (DataAccessException e) {
            // TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달
            return showMessageWithRedirect("DB 처리 과정 중 문제 발생", "/board/list", Method.GET, null, model);

        } catch (Exception e) {
            // TODO => 시스템에 문제가 발생하였다는 메시지를 전달
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list", Method.GET, null, model);
        }

        return showMessageWithRedirect("게시글 등록 완료", "/board/list", Method.GET, null, model);
    }

    @GetMapping("/list")
    public String openBoardList(Model model) {
        List<BoardDTO> boardDTOList = boardService.getBoardList();
        model.addAttribute("boardList", boardDTOList);

        return "/board/list";
    }

    @GetMapping(value = "/view")
    public String openBoardDetail(@RequestParam(value = "idx", required = false) Long idx, Model model) {
        if (idx == null) {
            // TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
            return "redirect:/board/list";
        }

        BoardDTO board = boardService.getBoardDetail(idx);
        if (board == null || "Y".equals(board.getDeleteYn())) {
            // TODO => 없는 게시글이거나, 이미 삭제된 게시글이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
            return "redirect:/board/list";
        }
        model.addAttribute("board", board);

        return "board/view";
    }

    @PostMapping(value = "/delete")
    public String deleteBoard(@RequestParam(value = "idx", required = false) Long idx, Model model) {
        if (idx == null) {
            // TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
            return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list", Method.GET, null, model);
        }

        try {
            boolean isDeleted = boardService.deleteBoard(idx);
            if (isDeleted == false) {
                // TODO => 게시글 삭제에 실패하였다는 메시지를 전달
                return showMessageWithRedirect("게시글 삭제 실패.", "/board/list", Method.GET, null, model);
            }
        } catch (DataAccessException e) {
            // TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달
            return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/list", Method.GET, null, model);
        } catch (Exception e) {
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list", Method.GET, null, model);
        }

        return showMessageWithRedirect("게시글 삭제가 완료되었습니다.", "/board/list", Method.GET, null, model);
    }

}
