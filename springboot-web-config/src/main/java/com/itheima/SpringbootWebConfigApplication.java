package com.itheima;

import com.itheima.utils.AliyunOSSOperator;
import com.itheima.utils.AliyunOSSProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SpringbootWebConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWebConfigApplication.class, args);
    }

    //使用方法一：
    //假设AliyunOSSOperator为第三方类，需要注入AliyunOSSProperties对象。使用@Bean注解（
    // 在项目启动的时候会自动调用有@Bean注解的方法），创建AliyunOSSOperator对象，返回的bean名字
    //为方法名。发现此类中有依赖其他类，则需要在形参中通过构造方法传入依赖的类对象（默认使用了
    // @Autowired注入）。
    @Bean
    public AliyunOSSOperator aliyunOSSOperator(AliyunOSSProperties aliyunOSSProperties){
        return new AliyunOSSOperator(aliyunOSSProperties);
    }
}
