package com.itheima.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
/**
 * 测试所有通知类型
 * */


@Aspect
@Slf4j
@Component
public class MyAspect1 {
    //提取切入点表达式
    @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
    private void pt() {}


    //前置通知--目标方法执行前执行
    @Before("pt()")
    public void before() {
        log.info("before执行了...");
    }

    //环绕通知--目标方法执行前后执行
    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("around--before执行了...");

        Object result = pjp.proceed();

        log.info("around--after执行了...");
        return result;
    }

    //后置通知--目标方法执行后执行
    @After("pt()")
    public void after() {
        log.info("after执行了...");
    }

    //返回后通知--目标方法正常返回后执行
    @AfterReturning("pt()")
    public void afterReturning() {
        log.info("afterReturning执行了...");
    }

    //异常后通知--目标方法异常返回后执行
    @AfterThrowing("pt()")
    public void afterThrowing() {
        log.info("afterThrowing执行了...");
    }
}
