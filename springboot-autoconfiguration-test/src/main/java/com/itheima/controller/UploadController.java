package com.itheima.controller;

import com.aliyun.oss.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
/**
 * 用于测试自定义starter 文件上传*/

@RestController
public class UploadController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public String upload(MultipartFile image) throws Exception {
        //上传文件到阿里云 OSS

        //引入自定义的依赖，并配置配置文件

        String url = aliyunOSSOperator.upload(image.getBytes(), image.getOriginalFilename());
        System.out.println( url);
        return url;


    }

}
