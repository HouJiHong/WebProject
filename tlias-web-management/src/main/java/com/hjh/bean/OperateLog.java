package com.hjh.bean;

import lombok.Data;

import java.time.LocalDateTime;
/**
 * 此类用于aop实现，封装操作增删改时的日志信息*/
@Data
public class OperateLog {
    private Integer id;
    private Integer operateEmpId;
    private LocalDateTime operateTime;
    private String className;
    private String methodName;
    private String methodParams;
    private String returnValue;
    private Long costTime;
}
