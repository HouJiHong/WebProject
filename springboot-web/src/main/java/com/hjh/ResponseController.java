package com.hjh;

//HTTP响应数据格式
//响应行（协议，状态码，状态码描述）
//响应头（响应头名：响应头值）
//响应体（响应内容）

//状态码
//1xx      响应中-临时状态码，表示请求已经接收，告诉客户端应该继续请求或者如果它已经完成则忽略它。
//2xx      成功-表示请求已经被成功接收，处理已完成。
//3xx      重定向-重定向到其他地方；让客户端再发起一次请求以完成整个处理。
//4xx      客户端错误-处理发生错误，责任在客户端。如：请求了不存在的资源、客户端未被授权、禁止访问等。
//5xx      服务器错误-处理发生错误，责任在服务端。如：程序抛出异常等。

//Web服务器对HTTP协议的响应数据进行了封装【HttpServletResponse】，并在调用Controller方法的时候传递给
//了该方法。这样，就使得程序员不必直接对协议进行操作，让Web开发更加便捷。

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ResponseController {
    //方法一：HttpServletResponse设置响应数据
    @RequestMapping("/response")
    public void response(HttpServletResponse response) throws IOException {
        //设置响应状态码
        response.setStatus(401);

        //设置响应头
        response.setHeader("name", "hjh");

        //设置响应体
        response.getWriter().write("<h1>hello world</h1>");

    }

    //方法二：使用ResponseEntity设置响应数据
    //将响应数据封装成ResponseEntity对象返回给客户端
    @RequestMapping("/response2")
    public ResponseEntity <String> response2() {
        return ResponseEntity.status(401).header("age", "18").body("<h1>hello world2</h1>");
    }

}
