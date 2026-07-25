package com.hjh.aop;

import com.hjh.bean.OperateLog;
import com.hjh.mapper.OperateLogMapper;
import com.hjh.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 此类为aop实现日志记录操作的切面类
 * */

//步骤：
    //1.引入aop依赖
    //2.创建数据库，和实体类来保存数据
    //3.使用注解方式来匹配切入点，所以创建注解类，并在需要切入的类添加此注解
    //4.创建切面类，实现相应逻辑


//ThreadLocal(应用场景：在同一个线程/同一个请求中，进行数据共享。)
//ThreadLocal并不是一个Thread，而是Thread的局部变量。
//ThreadLocal为每个线程提供一份单独的存储空间，具有线程隔离的效果，不同的线程之间不会相互干扰。

//ThreadLocal常用方法:
    //public void set(T value)  设置当前线程的线程局部变量的值
    //public T get()            返回当前线程所对应的线程局部变量的值
    //public void remove()      移除当前线程的线程局部变量

//每次登录的是不同的人（不同线程），而在登录后的每一次访问页面是同一个登录的人（同一线程），需要共享数据，所以需要使用ThreadLocal(不同线程数据隔离)
//在本例中，就是从登录后的每次访问页面时，从令牌中获取当前登录用户的id，将其保存在ThreadLocal中，方便后续使用（aop记录日志）。



//获取令牌中的id数据具体操作步骤：
//1．定义ThreadLocal操作的工具类，用于操作当前登录员工ID。
//2．在过滤器/拦截器中，解析完当前登录员工ID，将其存入ThreadLocal（用完之后需将其删除）。
//3．在AOP程序中，从ThreadLocal中获取当前登录员工的ID。

@Slf4j
@Aspect
@Component
public class OperationLogAspect {
    //通过构造函数注入mapper接口
    private final OperateLogMapper operateLogMapper;
    public OperationLogAspect(OperateLogMapper operateLogMapper) {
        this.operateLogMapper = operateLogMapper;
    }
    @Around("@annotation(com.hjh.anno.Log)")
    public Object logOperation(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();

        //执行目标方法
        Object result = pjp.proceed();

        //计算方法执行耗时
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;

        //封装日志信息
        OperateLog olog = new OperateLog();
        olog.setOperateEmpId(getCurrentEmpId());  //注意：这个id是当前登录用户的id，需要通过令牌获取
        olog.setOperateTime(LocalDateTime.now());
        olog.setClassName(pjp.getTarget().getClass().getName());
        olog.setMethodName(pjp.getSignature().getName());
        olog.setMethodParams(Arrays.toString(pjp.getArgs()));
        olog.setReturnValue(result != null? result.toString() : "void");
        olog.setCostTime(costTime);

        //记录日志
        log.info("记录操作日志：{}", olog);
        operateLogMapper.insert(olog);

        //返回结果
        return result;

    }
    private Integer getCurrentEmpId() {
        return CurrentHolder.getCurrentID();
    }
}
