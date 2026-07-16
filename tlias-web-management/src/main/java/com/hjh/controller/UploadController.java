package com.hjh.controller;

import com.hjh.bean.Result;
import com.hjh.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
//---------------------------------此为上传到本地-------------------------------------------------------
    /**
     * 文件上传（springboot文件上传默认大小不能超过1MB，不然就配置yml文件）
     * 1.前端通过表单标签进行文件管理器选择文件上传，发送请求，请求参数包括两个文本和一个文件
     * 2.SpringMVC处理请求，将文件参数封装为MultipartFile对象
     * 3.MultipartFile文件对象，会将文本和文件保存到服务器，保存路径为临时目录，用完就删除,所以需要及时保存
     * 4.Controller方法返回值类型为Result，将上传结果封装为Result对象，返回给前端
     */
    //不推荐直接将上传的文件直接保存在服务器中，1.浏览器无法直接访问保存的文件2.服务器容量有限制3.安全问题
    //解决方法：1.自己搭建文件存储服务2.使用第三方云服务 (云服务代表很多服务，这里只用对象存储服务)
    //阿里云对象存储OSS（ObjectStorageService），是云存储服务。使用OSS，
    //可以通过网络随时存储和调用包括文本、图片、音频和视频等在内的各种文件。
    //流程：1.注册阿里云 2.开通对象存储服务oss 3.创建Bucket（存储空间）4.获取并配置accessKey密钥
    //bucket:文件上传保存的地方
    //accessKey密钥保存好，以管理员身份打开CMD命令行，执行如下命令，配置系统的环境变量。
    //set OSS_ACCESS_KEY_ID=XXXXXXXXXXXXXXXXXXXXXX
    //set OSS_ACCESS_KEY_SECRET=XXXXXXXXXXXXXXXXXX
    //setx OSS_ACCESS_KEY_ID "%OSS_ACCESS_KEY_ID%"
    //setx OSS_ACCESS_KEY_SECRET "%OSS_ACCESS_KEY_SECRET%"
    //echo %OSS_ACCESS_KEY_ID%
    //echo %OSS_ACCESS_KEY_SECRET%

    //5.参考sdk编写代码
    //导入依赖
    //编写代码

    //注意：MultipartFile的参数名一定要和前端表单项的参数名一致，不然要用@RequestParam注解指定前端参数名
    @PostMapping("/uploadTest")
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

        //保存文件（直接保存在服务器中）
        file.transferTo(new File("E:/JavaProject/web-project/tlias-web-management/image/"+newFileName));

        return Result.success();
    }

    //---------------------------------此为上传到oss-------------------------------------------------------
    //调用工具类，实现文件上传
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public  Result upload(MultipartFile file) throws Exception {
        log.info("文件上传：{}",file.getOriginalFilename());
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传url:{}",url);
        return Result.success(url);//将文件的访问地址返回给前端
    }

}
