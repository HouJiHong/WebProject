package com.hjh.utils;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")//批量注入，选择配置项的前缀，即aliyun.oss.*
public class AliyunOSSOProperties {
    //将所有配置项都注入，变量名要一致
    private String endpoint;
    private String bucketName;
    private String region;
}
