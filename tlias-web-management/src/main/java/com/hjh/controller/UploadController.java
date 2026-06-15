package com.hjh.controller;

import com.hjh.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
//@RequestMapping("/emps")
public class UploadController {
    /**
     * 文件上传（springboot文件上传默认大小不能超过1MB，不然就配置yml文件）
     * 1.前端通过表单标签进行文件管理器选择文件上传，发送请求，请求参数包括两个文本和一个文件
     * 2.SpringMVC处理请求，将文件参数封装为MultipartFile对象
     * 3.MultipartFile文件对象，会将文本和文件保存到服务器，保存路径为临时目录，用完就删除,所以需要及时保存
     * 4.Controller方法返回值类型为Result，将上传结果封装为Result对象，返回给前端
     */
    //注意：MultipartFile的参数名一定要和前端表单项的参数名一致，不然要用@RequestParam注解指定前端参数名
    @PostMapping("/upload")
    public Result upload(String  name, Integer  age, MultipartFile  file) throws IOException {
        log.info("接收参数：{},{},{}", name, age, file);
        //获取原始文件名(注意：如果原始文件名与已保存的文件名重复，则会覆盖)
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID生成文件名
        String uuid = UUID.randomUUID().toString();
        //拼接文件名
        String newFileName = uuid + extension;

        //保存文件
        file.transferTo(new File("E:/JavaProject/web-project/tlias-web-management/image/"+newFileName));

        return Result.success();
    }
}
