package com.hjh.config;

import com.hjh.interceptor.DemoInterceptor;
import com.hjh.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**拦截器的配置类*/

//配置拦截路径
// /*           一级路径              能匹配/depts，/emps，/login，不能匹配 /depts/1
// /**          任意级路径            能匹配/depts，/depts/1,/depts/1/2
// /depts/*     /depts下的一级路径     能匹配/depts/1，不能匹配/depts/1/2，/depts
// /depts/**    /depts下的任意级路径   能匹配/depts，/depts/1,/depts/1/2，不能匹配/emps/1

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private DemoInterceptor demoInterceptor;

    @Autowired
    private TokenInterceptor tokenInterceptor;

    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(demoInterceptor).addPathPatterns("/**");//拦截所有请求
    }*/

    //注册拦截器，指定拦截器的拦截路径
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/login");  // 不拦截/login
    }
}
