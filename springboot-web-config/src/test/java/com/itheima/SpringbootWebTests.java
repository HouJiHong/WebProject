package com.itheima;

import cn.hutool.core.io.FileUtil;
import com.example.HeaderParser;
import com.example.TokenParser;
import com.itheima.utils.AliyunOSSOperator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.io.File;

@SpringBootTest
class SpringbootWebTests {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;


    //测试在默认情况下，将对象交给ioc容器中，创建的对象，作用域是单例的
    @Test
    public void testScope(){
        for (int i = 0; i < 5; i++) {
            Object deptController = applicationContext.getBean("deptController");
            System.out.println(deptController);
        }

    }

    @Test
    public void testUpload() throws Exception {
        File file = new File("E:\\桌面\\魔改.jpg");
        String url = aliyunOSSOperator.upload(FileUtil.readBytes(file), "1.jpg");
        System.out.println(url);
    }

    //使用方案一：第三方类使用@component注解，创建对象，并交给ioc容器管理。在使用的时候需要自己
     //配置扫描器，将第三方类加入扫描范围。
    @Autowired
    private TokenParser tokenParser;

    @Test
    public void testTokenParser(){
        tokenParser.parse();
    }

    //使用方案二：在启动类中使用@import注解，将第三方类加入ioc容器中。
    @Autowired
    private HeaderParser headerParser;
    @Test
    public void testHeaderParser(){
        headerParser.parse();
    }
}
