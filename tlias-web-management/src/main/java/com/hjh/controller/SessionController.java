package com.hjh.controller;

import com.hjh.bean.Result;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 演示会话跟踪技术（cookie，session）*/

@Slf4j
@RestController
public class SessionController {
    //设置cookie
    @GetMapping("/c1")
    public Result cookie1(HttpServletResponse  response){
        response.addCookie(new Cookie("name","hjh"));
        return Result.success();
    }

    //获取cookie
    @GetMapping("/c2")
    public Result cookie2(HttpServletRequest  request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("name")){
                System.out.println("Cookie内容：name:"+cookie.getValue());
            }
        }
        return Result.success();
    }

    @GetMapping("/s1")
    public Result session1(HttpSession session){
        log.info("httpsession-s1:{}",session.hashCode());
        session.setAttribute("loginUser","abc");
        return Result.success();
    }

    @GetMapping("/s2")
    public Result session2(HttpSession session){
        log.info("httpsession-s2:{}",session.hashCode());
        Object loginUser = session.getAttribute("loginUser");
        log.info("loginUser:"+loginUser);
        return Result.success();
    }
}
