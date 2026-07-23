package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 测试通过参数ProceedingJoinPoint，和JoinPoint获取连接点的相关信息
 * */

//连接点
//●在Spring中用JoinPoint抽象了连接点，用它可以获得方法执行时的相关信息，如目标类名、方法名、方法参数等。
//>对于@Around通知，获取连接点信息只能使用ProceedingJoinPoint
//>对于其它四种通知，获取连接点信息只能使用JoinPoint，它是ProceedingJoinPoint的父类型

@Slf4j
//@Aspect
@Component
public class MyAspect6 {
    @Around("execution(* com.itheima.service.*.*(*))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("MyAspect6--around执行了...");
        String className = pjp.getTarget().getClass().getName(); //获取目标类名
        log.info("目标类名：{}", className);
        Signature signature = pjp.getSignature(); //获取目标方法签名
        log.info("目标方法签名：{}", signature);
        String methodName = pjp.getSignature().getName(); //获取目标方法名
        log.info("目标方法名：{}", methodName);
        Object[] args = pjp.getArgs(); //获取目标方法运行参数
        log.info("目标方法运行参数：{}", Arrays.toString(args));
        Object res = pjp.proceed(); //执行目标方法
        return res;
    }

    @Before("execution(* com.itheima.service.*.*(*))")
    public void before(JoinPoint joinPoint){
        log.info("MyAspect6--before执行了...");
        String className = joinPoint.getTarget().getClass().getName(); //获取目标类名
        Signature signature = joinPoint.getSignature(); //获取目标方法签名
        String methodName = joinPoint.getSignature().getName(); //获取目标方法名
        Object[] args = joinPoint.getArgs(); //获取目标方法运行参数
    }

}
