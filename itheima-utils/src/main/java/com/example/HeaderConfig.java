package com.example;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

//自动配置 -@Conditional
//●作用：按照一定的条件进行判断，在满足给定条件后才会注册对应的bean对象到springIoc容器中。
//位置：方法、类。注解在类上时且满足时，如果方法上也有注解，方法上满足的，才会被注册到springIoc容器中。
//●@Conditional本身是一个父注解，派生出大量的子注解：
//比如：
//@ConditionalOnClass：判断环境中是否有对应字节码文件，才注册bean到Ioc容器。
//@ConditionalOnMissingBean：判断环境中没有对应的bean（类型或名称），才注册bean到Ioc容器。
//@ConditionalOnProperty：判断【配置文件】中有对应属性和值，才注册bean到Ioc容器。

@Configuration
public class HeaderConfig {

    @Bean
    @ConditionalOnClass(name = "io.jsonwebtoken.Jwts")//判断环境中是否有io.jsonwebtoken.Jwts字节码文件，
                                                        // 有则注册bean到springIoc容器中。
    @ConditionalOnBean //判断环境中有没有对应的bean，没有则注册bean到springIoc容器中。
    @ConditionalOnProperty(name = "myName",havingValue = "Hjh") //判断【配置文件】中有没有myName属性，
                                                            // 且属性值为Hjh，有且一致则注册bean到springIoc容器中。
    public HeaderParser headerParser(){
        return new HeaderParser();
    }

    @Bean
    public HeaderGenerator headerGenerator(){
        return new HeaderGenerator();
    }
}
