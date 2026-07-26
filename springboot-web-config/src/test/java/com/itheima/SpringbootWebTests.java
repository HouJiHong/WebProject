package com.itheima;

import cn.hutool.core.io.FileUtil;
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
}
