package com.hjh.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 过滤器按照类名进行排序执行，在DemoFilter前执行*/

//@WebFilter(urlPatterns = "/*")
@Slf4j
public class ADemoFilter implements Filter {


    //初始化方法，web服务器启动时调用一次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init 初始化方法");
    }

    //拦截到请求时调用，执行多次。
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("拦截到请求--放行前");

        //放行(不放行永远都不会访问资源)
        filterChain.doFilter(servletRequest, servletResponse);


        log.info("拦截请求--放行后");
    }

    //销毁方法，web服务器关闭时调用一次
    @Override
    public void destroy() {
        log.info("destroy 销毁方法");
    }

}
