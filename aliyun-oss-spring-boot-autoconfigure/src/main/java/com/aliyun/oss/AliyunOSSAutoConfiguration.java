package com.aliyun.oss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
