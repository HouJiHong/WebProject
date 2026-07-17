package com.hjh.exception;

import com.hjh.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 在三层架构中出现的异常会一层一层往上抛，定义了全局异常处理器后，最后会抛给它来处理
 * */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleException(Exception e){
        log.error("服务器发生异常：", e);
        return Result.error("服务器发生异常");
    }

    //想捕获什么异常就在形参中填写
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e){
        log.error("填写错误");
        //获取错误信息
        String message = e.getMessage();
        //定位错误信息
        String errMsg = message.substring(message.indexOf("Duplicate entry"));
        //得到错误字段
        String[] s = errMsg.split(" ");
        return Result.error(s[2]+"已存在");
    }
}
