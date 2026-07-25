package com.hjh.interceptor;

import com.hjh.utils.CurrentHolder;
import com.hjh.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Map;

/**
 * 使用拦截器进行请求拦截，并校验令牌，其配置类在com.hjh.config.WebConfig*/

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1. 获取请求路径
        String requestURI = request.getRequestURI();// 获得的是访问的资源路径

        //2．判断是否是登录请求，如果路径中包含/login，说明是登录操作，放行
        if (requestURI.contains("/login")) {
            log.info("登录请求，放行");

            return true;
        }

        //3．获取请求头中的token
        String token = request.getHeader("token");

        //4. 判断token是否存在，如果不存在，说明用户没有登录，返回错误信息（响应401状态码）
        if (token == null || token.isEmpty()) {
            log.info("令牌为空，响应401");
            response.setStatus(401);
            return false;
        }

        //5. 如果token存在，校验令牌，如果校验失败->返回错误信息（响应401状态码）
        try {
            //先解析令牌,在解析的过程中有任何异常，直接返回错误，成功后，获取用户id，将用户id存入ThreadLocal，
            //以便后续操作时需要使用当前的操作用户
            Map<String, Object> stringObjectMap = JwtUtils.parseToken(token);
            Integer empId = Integer.valueOf(stringObjectMap.get("id").toString());
            CurrentHolder.setCurrentID(empId); //保存当前用户id到ThreadLocal中
            log.info("令牌校验通过，当前用户id为：{}，将其存入ThreadLocal", empId);


        } catch (Exception e) {
            log.info("令牌有误，响应401");
            response.setStatus(401);
            return false;
        }


        //6. 校验通过，放行
        return true;


    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //所有的操作完成后，移除ThreadLocal中的数据 (注意：这是拦截器，需要在afterCompletion()方法中执行删除，
        // 在过滤器中，只需要在最后放行后删除就行)
        CurrentHolder.remove();
    }
}
