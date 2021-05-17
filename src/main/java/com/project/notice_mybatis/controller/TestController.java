package com.project.notice_mybatis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController //자동으로 @ResponseBody를 붙여주는거랑 같음.
//@Controller
public class TestController {

    @GetMapping("/")
//    @ResponseBody
    public String home(){
        String home = "home";
        return home;
    }


    @GetMapping("/message")
    @ResponseBody //public @ResponseBody 메서드(){}도 가능.
    public String testResponseBody(){
        //@ResponseBody가 붙으면 Spring MessageConverter에의해 화면(HTML)이 아닌 return 타입에 해당하는 데이터 리턴.
        String message = "message 출력";
        return message;
    }

    @GetMapping(value = "/members")
    @ResponseBody // public @ResponseBody String testByResponseBody()와 같이 리턴 타입 좌측에 지정 가능
    public Map<Integer, Object> testByResponseBody() {

        Map<Integer, Object> members = new HashMap<>();

        for (int i = 1; i <= 20; i++) {
            Map<String, Object> member = new HashMap<>();
            member.put("idx", i);
            member.put("nickname", i + "길동");
            member.put("height", i + 20);
            member.put("weight", i + 30);
            members.put(i, member);
        }

        return members;
    }

}
