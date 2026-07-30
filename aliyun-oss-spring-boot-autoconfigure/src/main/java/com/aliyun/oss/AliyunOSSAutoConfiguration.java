package com.aliyun.oss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//自定义starter
//●需求：自定义aliyun-oss-spring-boot-starter，完成阿里云oss操作工具类AliyunOSSOperator的自动配置。
//●目标：引入起步依赖引入之后，要想使用阿里云oss，注入Aliyunossoperator直接使用即可。
//● 步骤:
//1.创建aliyun-oss-spring-boot-starter模块(只需配置autoconfigure的依赖)
//2.创建aliyun-oss-spring-boot-autoconfigure模块，在starter中引入该模块
//3.在aliyun-oss-spring-boot-autoconfigure模块中的定义自动配置功能(引入oss依赖，并编写上传功能
// 编写AutoConfiguration引入bean用于注入，如果bean还存在依赖关系，通过EnableConfigurationProperties
// 注解import注入)，
// 并定义自动配置文件META-INF/spring/xxxx.imports



/**
 * AliyunOSS的自动配置类
 * */

@EnableConfigurationProperties(AliyunOSSOProperties.class)
@Configuration
public class AliyunOSSAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public AliyunOSSOperator aliyunOSSOperator(AliyunOSSOProperties aliyunOSSOProperties){
        return new AliyunOSSOperator(aliyunOSSOProperties);
    }
}
