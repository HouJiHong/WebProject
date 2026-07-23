package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 2，3，4用于测试切面类的优先级
 * */

@Slf4j
@Component
//@Aspect
@Order(3) // 设置切面的优先级
public class MyAspect2 {
    //前置通知
    @Before("execution(* com.itheima.service.impl.*.*(..))")
    public void before() {
        log.info("MyAspect2--before执行了...");
    }

    //后置通知
    @After("execution(* com.itheima.service.impl.*.*(..))")
    public void after() {
        log.info("MyAspect2--after执行了...");
    }
}
