package com.project.notice_mybatis.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.notice_mybatis.adapter.GsonLocalDateTimeAdapter;
import com.project.notice_mybatis.domain.CommentDTO;
import com.project.notice_mybatis.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    //PostMan으로 확인.
    @GetMapping("/comments/{boardIdx}")
    public JsonObject getCommentList(@PathVariable("boardIdx") Long boardIdx,
                                     @ModelAttribute("params") CommentDTO params) {
        //@PathVariable : @RequestParam과 유사기능으로 REST방식에서 리소스 표현시 사용. URI파라미터로 전달받을 변수 지정가능.

        //JSON 객체 생성.
        JsonObject jsonObj = new JsonObject();

        List<CommentDTO> commentList = commentService.getCommentList(params);
        if (CollectionUtils.isEmpty(commentList) == false) {

            //Adapter추가로 LocalDateTime이 JSON Object에서 String 변경.
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                    new GsonLocalDateTimeAdapter()).create();

            JsonArray jsonArr = gson.toJsonTree(commentList).getAsJsonArray();
            jsonObj.add("commentList", jsonArr);
        }

        //JsonArray 객체 리턴않고 JSON 객체에 추가하는 이유
        //JsonArray 객체에 리턴해도되지만 JSON 객체에 담으면 여러타입 데이터를 추가할 수 있기때문에.
        return jsonObj;
    }


    //comments : 새 댓글, comments/{idx} : 댓글 수정.
    //RequestMethod.POST/PATCH : HTTP요청 POST/PATCH
    @RequestMapping(value = { "/comments", "/comments/{idx}" },
            method = { RequestMethod.POST, RequestMethod.PATCH })
    public JsonObject registerComment(@PathVariable(value = "idx", required = false) Long idx,
                                      @RequestBody final CommentDTO params) {

        JsonObject jsonObj = new JsonObject();

        try {
            //댓글 idx 존재시 수정.
            if (idx != null) {
                params.setIdx(idx);
            }

            boolean isRegistered = commentService.registerComment(params);
            jsonObj.addProperty("result", isRegistered);

        } catch (DataAccessException e) {
            jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

        } catch (Exception e) {
            jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
        }

        return jsonObj;
    }

}
