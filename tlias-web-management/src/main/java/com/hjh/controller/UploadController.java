package com.hjh.controller;

import com.hjh.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {
    /**
     * 文件上传
     * 1.前端通过表单标签进行文件管理器选择文件上传，发送请求，请求参数包括两个文本和一个文件
     * 2.SpringMVC处理请求，将文件参数封装为MultipartFile对象
     * 3.MultipartFile文件对象，会将文本和文件保存到服务器，保存路径为临时目录，用完就删除
     * 4.Controller方法返回值类型为Result，将上传结果封装为Result对象，返回给前端
     */
    //注意：MultipartFile的参数名一定要和前端表单项的参数名一致，不然要用@RequestParam注解指定前端参数名
    @PostMapping("/upload")
    public Result upload(String  name, Integer  age, MultipartFile  file) {
        log.info("接收参数：{},{},{}", name, age, file);
        return Result.success();
    }
}
