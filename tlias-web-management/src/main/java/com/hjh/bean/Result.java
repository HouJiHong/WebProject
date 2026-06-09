package com.hjh.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String code;//响应码:1成功，0失败
    private String msg;//错误信息
    private Object data;//数据

    public static Result success(){
        Result result = new Result();
        result.code = "1";
        result.msg = "成功";
        return result;
    }

    public static Result success(Object object){
        Result result = new Result();
        result.data = object;
        result.code = "1";
        result.msg = "成功";
        return result;
    }

    public static Result error(String msg){
        Result result = new Result();
        result.code = "0";
        result.msg = msg;
        return result;
    }
}
