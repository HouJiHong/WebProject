package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//AOP：Aspect Oriented Programming（面向切面编程、面向方面编程），可简单理解为就是面向特定方法编程。
//场景：案例中部分业务方法运行较慢，定位执行耗时较长的方法，此时需要统计每一个业务方法的执行耗时。
//一旦定义过了aop配置类，则SpringBoot会自动创建一个代理对象，代理对象会替代原始对象，注入到ioc容器中，
// 以后要想注入原始对象，都会从容器中拿到改造后的代理对象。

//操作步骤
//1. 导入依赖：在pom.xml中引入AOP的依赖\
//2. 编写AOP程序：针对于特定的方法根据业务需要进行编程

//常见概念
//  连接点：JoinPoint，可以被AOP控制的方法（暗含方法执行时的相关信息）
//  通知：Advice，指那些重复的逻辑，也就是共性功能（最终体现为一个方法）
//  切入点：PointCut，匹配连接点的条件，通知仅会在切入点方法执行时被应用
//  切面：Aspect，描述通知与切入点的对应关系（通知+切入点）
//  目标对象：Target，通知所应用的对象

//通知类型
//根据通知方法执行时机的不同，将通知类型分为以下常见的五类：
//1.@Around：环绕通知，此注解标注的通知方法在 目标方法前、后 都被执行
//2.@Before：前置通知，此注解标注的通知方法在 目标方法前 被执行
//3.@After：后置通知，此注解标注的通知方法在 目标方法后 被执行，无论是否有异常 都会 执行
//4.@AfterReturning: 返回后通知，此注解标注的通知方法在 目标方法后 被执行，有 异常 不会执行
//5.@AfterThrowing: 异常后通知，此注解标注的通知方法发生 异常后执行
//注意1:
//@Around环绕通知需要自己调用ProceedingJoinPoint.proceed()来让原始方法执行，其他通知不需要考虑目标方法执行
//注意2:
//@Around环绕通知方法的返回值，必须指定为object，来接收原始方法的返回值。

//通知顺序
//●当有多个切面的切入点都匹配到了目标方法，目标方法运行时，多个通知方法都会被执行。
//执行顺序：
//  不同切面类中，默认按照切面类的类名字母排序：
//  目标方法前的通知方法：字母排名靠前的先执行
//  目标方法后的通知方法：字母排名靠前的后执行

//用@order（数字）加在切面类上来控制顺序
//目标方法前的通知方法：数字小的先执行
//目标方法后的通知方法：数字小的后执行

@Slf4j
@Aspect // 表示当前类是一个aop切面类
@Component
public class RecordTimeAspect {
    //@Around指定该方法在com.itheima.service.impl包及其子包下的所有方法上执行
    @Around("execution(* com.itheima.service.impl.*.*(..))")  //切入点表达式
    public Object recordTime(ProceedingJoinPoint pjp) throws Throwable {    //定义一个 通知 方法，用于记录方法执行时间，
                                                                             //形参ProceedingJoinPoint用于执行原始方法，返回值取决于原始方法
        //1.记录开始时间
        long start = System.currentTimeMillis();


        //2.执行原始方法
        Object result = pjp.proceed();


        //3.记录结束时间,记录耗时
        long end = System.currentTimeMillis();
        log.info("方法 {}，耗时：{}ms",pjp.getSignature(),end-start);

        //4.返回结果
        return result;
    }
}
