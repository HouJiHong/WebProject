package com.hjh.utils;

/**ThreadLocal用于保存登录完成后的正在操作用户id，以便后续需要知道是谁在操作系统时提供(比如aop记录日志时要插入id)*/
public class CurrentHolder {

    private static final ThreadLocal<Integer> CURRENT_LOCAL = new ThreadLocal<>();
    public static void setCurrentID(Integer employeeId){
        CURRENT_LOCAL.set(employeeId);
    }
    public static Integer getCurrentID(){
        return CURRENT_LOCAL.get();
    }

    public static void remove(){
        CURRENT_LOCAL.remove();
    }

}
