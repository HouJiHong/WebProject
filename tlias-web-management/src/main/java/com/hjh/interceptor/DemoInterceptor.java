package com.hjh.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//拦截器（Interceptor）
//●概念：是一种动态拦截方法调用的机制，类似于过滤器。Spring框架中提供的，主要用来动态拦截 控制器 方法的执行。
//作用：拦截请求，在指定的方法调用前后，根据业务需要执行预先设定的代码。


//1．定义拦截器，实现HandlerInterceptor接口，并实现其所有方法。
//2. 定义一个配置类实现webMvcConfigurer接口，注册拦截器

//如果既配置了拦截器，又配置了过滤器，那么过滤器先执行，拦截器后执行，访问完资源，
//  先返回给拦截器执行，再返回给过滤器执行，最后返回给浏览器。


//Filter 与 Interceptor 区别:
//1．接口规范不同：过滤器需要实现Filter接口，而拦截器需要实现HandlerInterceptor接口。
//2．拦截范围不同：过滤器Filter会拦截所有的资源，而Interceptor只会拦截Spring环境中的资源。


@Slf4j
@Component
public class DemoInterceptor implements HandlerInterceptor {
    //在访问资源之前拦截，返回true表示放行，返回false表示不放行（过滤器想要放行就用filterChain.doFilter()）
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        return true;
    }


    //在访问资源之后运行 (可以没有，与过滤器不同，这里访问资源后回到放行处，需要自己定义方法来编写执行放行后的逻辑。
    //                           过滤器是不需要自己单独定义一个方法，接着放行代码往下写就行了)
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }


    //视图渲染完毕后运行 (可以没有)
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info("afterCompletion");
    }




}
