package com.hjh;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//请求处理类
@RestController
public class HelloController {

    @RequestMapping("/hello") //请求路径
    public String hello(String name) {
        System.out.println("hello world1 "+ name);
        return "hello world2 "+ name;
    }
}
