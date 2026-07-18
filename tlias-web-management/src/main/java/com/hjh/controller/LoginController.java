package com.hjh.controller;

import com.hjh.bean.Emp;
import com.hjh.bean.LoginInfo;
import com.hjh.bean.Result;
import com.hjh.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//登录接口做完后发现，无论是否登录，都可以直接访问其他业务接口，登录毫无意义，所以需要登录标记来拦截访问
//登录标记：用户登录成功之后，在后续的每一次请求中，都可以获取到该标记。【会话技术]
//统一拦截：过滤器Filter、拦截器Interceptor

//会话：用户打开浏览器，访问web服务器的资源，会话建立，直到有一方断开连接，会话结束。在一次会话中可以包含多次请求和响应。
//会话跟踪：一种维护浏览器状态的方法，服务器需要识别多次请求是否来自于同一浏览器，以便在同一次会话的多次请求间共享数据。
//会话跟踪方案：
//·客户端会话跟踪技术：Cookie   (浏览器访问服务器，先登录，成功后服务器会将所有需要共享的数据生成cookie响应给浏览器，
//                             浏览器会将cookie存储在浏览器，之后浏览器每次请求服务器都会携带cookie，
//                             服务器只需要检测有没有，没有就重新登陆)
//优点：HTTP协议中支持的技术
//缺点：
// 移动端APP无法使用Cookie
// 不安全，用户可以自己禁用Cookie
// Cookie不能跨域，所以不支持前后端分离开发(访问前端服务器和后端服务器时，服务器的协议，ip/域名，端口，都一致就不是跨域)



//·服务端会话跟踪技术：Session   (底层是基于cookie技术，当浏览器完成登录开始会话，服务器生成一个唯一标识jsessionid,
//                              并保存在服务器本地，然后响应浏览器，把jsessionid也传给浏览器，浏览器也保存在本地，
//                              以后的每一次请求都要发送jsessionid,服务器检测jsessionid一致性)
//优点：存储在服务端，安全
//缺点:
// 服务器集群环境下无法直接使用Session（因为每访问一个服务器都要查找有没有保存相应的jsessionid）
// Cookie的所有缺点



//·令牌技术     (当浏览器完成登录开始会话，服务器根据编写要求生成身份令牌返回给浏览器，浏览器保存在本地，以后每次请求都要
//                  携带令牌，服务器检测令牌的有效性)
//优点：
// 支持PC端、移动端
// 解决集群环境下的认证问题
// 减轻服务器端存储压力
//缺点：需要自己实现


//JWT令牌
//●全称：JSON Web Token
//●定义了一种简洁的、自包含的格式，用于在通信双方以json数据格式安全的传输信息。
//●组成：
//>第一部分：Header(头），记录令牌类型、签名算法等。例如：{"alg":"HS256","type":"JWT"}
//>第二部分：Payload(有效载荷），携带一些自定义信息、默认信息等。例如：{"id"："1","username"："Tom"}
//>第三部分：Signature(签名），防止Token被篡改、确保安全性。将header、payload融入，并加入指定秘钥，通过指定签名算
//法计算而来。

//将一，二个部分通过Base64编码方式 与 第三部分通过指定签名算法计算得来的加密字符串 连接起来，中间用点隔开，生成令牌。
// Base64：是一种基于64个可打印字符（A-Z a-z 0-9 + /）来表示二进制数据的编码方式。


@Slf4j
@RestController
public class LoginController {
    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp){
        log.info("登录：{}",emp);
        LoginInfo info = empService.login(emp);
        if(info != null){
            return Result.success(info);
        }
        return Result.error("用户名或密码错误");
    }
}
