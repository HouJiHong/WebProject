package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 测试切入点表达式
 * */

//作用：用来决定项目中的哪些方法需要加入通知
//常见形式：
    //1.execution(修饰符？返回值 包名.类名.?方法名(参数列表) throws 异常？) 根据方法的签名来匹配，问号前面的可以省略
    //可以使用通配符描述切入点：
      //*:单个独立的符号，可以匹配任意返回值，包名，方法名，任意类型的【一个参数】，也可以通配包，类，方法名的一部分
      //execution(* com.*.service.*.update*(*))  注意：这个例子匹配包的时候，没有匹配包下的子包，而是通过接口描述切入点

      //..: 多个连续的任意符号，可以通配任意层次的包，或任意类型，任意个数的参数
      //execution(* com.hjh..DeptService.*(..))

    //根据业务需求可以使用 且&&，或||，非！的组合进行切入点匹配

    //2.@annotation(注解) 根据方法上的注解来匹配
      //步骤:
      //1.创建一个注解
      //2.在需要切入的类方法中添加该注解
      //3.创建切面类，并添加切点方法，并添加@annotation(注解全类名)

@Slf4j
@Component
//@Aspect
public class MyAspect5 {
    //前置通知
    //@Before("execution(public void com.itheima.service.impl.DeptServiceImpl.delete(java.lang.Integer))")
    //@Before("execution( void com.itheima.service.impl.DeptServiceImpl.delete(java.lang.Integer))") //省略？
    //@Before("execution( void delete(java.lang.Integer))") //省略包名和类名（不推荐）
    //@Before("execution(* com.itheima.service.impl.*.*(*))")
    //@Before("execution(* com.itheima.service.*.*(*))") //通过接口匹配（尽量通过基于接口描述）

    //匹配list与delete方法
    /*@Before("execution(* com.itheima.service.impl.DeptServiceImpl.list(..)) ||" +
            "execution(* com.itheima.service.impl.DeptServiceImpl.delete(..)) ")*/

    @Before("@annotation(com.itheima.anno.LogOperation)") //要声明注解的全类名
    public void before() {
        log.info("MyAspect5--before执行了...");
    }
}
