package com.hjh.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

//过滤器（Filter）
//概念：Filter过滤器，是JavaWeb三大组件(Servlet、Filter、Listener)之一。
//过滤器可以把对资源的请求拦截下来，从而实现一些特殊的功能。
//过滤器一般完成一些通用的操作，比如：登录校验、统一编码处理、敏感字符处理等。

//1.定义Filter：定义一个类，实现Filter接口，并实现其所有方法。
//2.配置Filter：Filter类上加@webFilter注解，配置拦截路径。
//              在启动引导类上加@ServletComponentScan开启Servlet组件支持。

//Filter-过滤器链  (过滤器的doFilter方法的FilterChain参数，其实就是过滤器链，放行操作就是执行下一个过滤器，
//                     当没有下一个过滤器后就访问资源，访问完资源后就从后往前执行每个过滤器放行后的操作)
//介绍：一个web应用中，可以配置多个过滤器，这多个过滤器就形成了一个过滤器链。
//顺序：注解配置的Filter，优先级是按照过滤器【类名】（字符串）的自然排序。



//@WebFilter(urlPatterns = "/*")//拦截所有请求   /login 只拦截/login请求，/login/* 拦截/login下的所有请求
@Slf4j
public class DemoFilter implements Filter {

    //初始化方法，web服务器启动时调用一次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init 初始化方法");
    }

    //拦截到请求时调用，执行多次。在放行前会执行放行前的逻辑，放行后执行完请求资源会返回 放行处 执行剩余的逻辑。
    // 最后响应浏览器
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("拦截到请求--放行前");

        //放行(不放行永远都不会访问资源)
        filterChain.doFilter(servletRequest,servletResponse);


        log.info("拦截请求--放行后");
    }

    //销毁方法，web服务器关闭时调用一次
    @Override
    public void destroy() {
        log.info("destroy 销毁方法");
    }
}
