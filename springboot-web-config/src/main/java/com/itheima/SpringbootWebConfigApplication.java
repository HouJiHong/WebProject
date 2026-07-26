package com.itheima;

import com.example.EnableHeaderConfig;
import com.example.HeaderConfig;
import com.example.MyImportSelector;
import com.example.TokenParser;
import com.itheima.utils.AliyunOSSOperator;
import com.itheima.utils.AliyunOSSProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
//起步依赖：就是maven的自动依赖

//什么是自动配置
//●SpringBoot的自动配置就是当spring项目启动后，一些配置类、bean对象就自动存入到了Ioc容器中，不需要我们
//手动去声明，从而简化了开发，省去了繁琐的配置操作。

//方案一：使用@Component注解，将类交给ioc容器管理（繁琐，需要手动配置扫描器，不推荐）

//方案二：使用@import
//@Import导入。@Import导入的类会被Spring加载到Ioc容器中，导入形式主要有以下几种：
//1.导入 普通类
//2.导入 配置类
//3.导入ImportSelector接口实现类
//4.自定义@EnableXxxx注解，封装@import注解





//方案一 （不推荐）
//@ComponentScan(basePackages = {"com.itheima", "com.example"}) //和构造器一样，一旦自定义声明，默认的
                                                                //组件扫描范围被覆盖，需要重新声明

//方案二
//@Import(TokenParser.class)//普通类，类可以没有@Component注解
//@Import(HeaderConfig.class)//配置类，类需要有@Configuration注解,会将里面的bean都添加到ioc容器中
//@Import(MyImportSelector.class) //importSelector的实现类 -批量导入

@EnableHeaderConfig  //自定义注解类，在注解中配置了@Import导入关键类，实现批量导入（推荐）
@SpringBootApplication //具备组件扫描的功能，但是默认扫描当前包及其子包下的所有组件
public class SpringbootWebConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWebConfigApplication.class, args);
    }

    //使用方法一：(不推荐)
    //假设AliyunOSSOperator为第三方类，需要注入AliyunOSSProperties对象。使用@Bean注解（
    // 在项目启动的时候会自动调用有@Bean注解的方法），创建AliyunOSSOperator对象，返回的bean名字
    //为方法名。发现此类中有依赖其他类，则需要在形参中通过构造方法传入依赖的类对象（默认使用了
    // @Autowired注入）。
    /*@Bean
    public AliyunOSSOperator aliyunOSSOperator(AliyunOSSProperties aliyunOSSProperties){
        return new AliyunOSSOperator(aliyunOSSProperties);
    }*/
}
