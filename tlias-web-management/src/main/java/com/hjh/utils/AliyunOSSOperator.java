package com.hjh.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

//此为工具类：用于文件上传到OSS
@Component
public class AliyunOSSOperator {
    //这三个参数容易变更，转移到配置文件中,使用@value注解引入
//    private String endpoint = "https://oss-cn-beijing.aliyuncs.com";
//    private String bucketName = "java-web-project-upload";
//    private String region = "cn-beijing";

    //还是太麻烦，如果有很多配置项，需要一个一个配。此时可以用一个实体类来注入
    /*@Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Value("${aliyun.oss.region}")
    private String region;*/

    //注入实体类,在哪个方法中哪个配置项需要，就get哪个
    @Autowired
    private AliyunOSSOProperties aliyunOSSOProperties;


    public String upload(byte[] content, String originalFilename) throws Exception {
        //将需要的配置项get，并保存在变量中
        String endpoint = aliyunOSSOProperties.getEndpoint();
        String bucketName = aliyunOSSOProperties.getBucketName();
        String region = aliyunOSSOProperties.getRegion();


        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        //生成文件目录和文件名
        //填写Object完整路径，例如2024/06/1.png。Object完整路径中不能包含Bucket名称。
        //获取当前系统日期的字符串,格式为 yyyy/MM
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        //生成一个新的不重复的文件名
        String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = dir + "/" + newFileName;

        // 创建OSSClient实例。（不用看）
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();

        try {
            //提交文件（文件的容器名，保存的路径，文件的字节流）
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));
        } finally {
            ossClient.shutdown();
        }
        //返回文件保存后，文件的地址（模板：https://容器名.域名/文件保存的路径）
        return endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;
    }

}
