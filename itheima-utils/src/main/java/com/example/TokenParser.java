package com.example;

import org.springframework.stereotype.Component;
//起步依赖：就是maven的自动依赖

//什么是自动配置
//●SpringBoot的自动配置就是当spring项目启动后，一些配置类、bean对象就自动存入到了Ioc容器中，不需要我们
//手动去声明，从而简化了开发，省去了繁琐的配置操作。

//方案一：使用@Component注解，将类交给ioc容器管理（繁琐，需要手动配置扫描器，不推荐）
//@Component
public class TokenParser {

    public void parse(){
        System.out.println("TokenParser ... parse ...");
    }

}
