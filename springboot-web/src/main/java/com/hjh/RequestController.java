package com.hjh;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//HTTP请求数据格式
//请求行（请求方式，资源路径，协议）
//请求头（请求头名：请求头值）
//请求体（POST请求，存放参数）

//Web服务器(Tomcat)对HTTP协议的请求数据进行解析，并进行了封装【HttpServletRequest】，在调用Controller方
//法的时候传递给了该方法。这样，就使得程序员不必直接对协议进行操作，让Web开发更加便捷。




@RestController
public class RequestController {
    @RequestMapping("/request")
    public String request(HttpServletRequest request) {
        //获取请求方式
        String method = request.getMethod();
        System.out.println("请求方式："+method);

        //获取请求路径
        StringBuffer url = request.getRequestURL();//http://localhost:8080/request
        System.out.println("请求路径："+url);
        String uri = request.getRequestURI();// /request
        System.out.println("请求URI："+uri);

        //获取请求协议
        String protocol = request.getProtocol();
        System.out.println("请求协议："+protocol);

        //获取请求头（表示浏览器能接受的资源类型）
        String Accept = request.getHeader("Accept");
        System.out.println("Accept："+Accept);


        //获取请求参数
        String name = request.getParameter("name");
        System.out.println("请求参数："+name);
        String age = request.getParameter("age");
        System.out.println("请求参数："+age);

        return "OK";
    }
}
