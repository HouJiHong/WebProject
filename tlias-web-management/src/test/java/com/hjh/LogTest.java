package com.hjh;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
//快速入门

public class LogTest {
    //创建日志对象
    private static final Logger log = LoggerFactory.getLogger(LogTest.class);
    @Test
    public void testLog(){
        //System.out.println(LocalDateTime.now()+"开始计算");
        log.debug("开始计算");
        int sum = 0;
        int[] nums = {1,2,3,4,5,6,7,8,9,10};
        for (int num: nums){
            sum += num;
        }

        log.info("结果为："+sum);
        log.debug("结束计算");
        //System.out.println(LocalDateTime.now()+"计算结束");
        //System.out.println("结果为："+sum);
    }
}
