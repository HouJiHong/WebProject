package com.hjh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//前后端联调测试
//前端工程请求nginx服务器的地址为http://localhost:90/api/depts
//nginx的反向代理：反向代理是一种网络架构，通过代理服务器为后端的服务器做代理，客户端的请求直接请求代理服务器，
// 然后转发给后端的服务器。（安全：后端服务器的信息全部隐藏，只暴露代理服务器的信息、灵活：添加后端服务器只需要在代理服务器上修改、
// 							负载均衡：代理服务器根据后端服务器的访问压力，调整访问）

//在nginx.conf中添加反向代理如下内容：
//server {
//  	listen 90;   监听端口90
//  	......
//  	location ^~ /api/ {
//  		rewrite ^/api/(.*)$ /$1 break;  将/api/替换成/
//  		proxy_pass http://127.0.0.1:8080;
//  	}
//}
//1.location：用于定义匹配路径匹配的规则。
//2.^~ /api/：表示精确匹配，即只匹配以/api/开头的路径。
//3.rewrite：该指令用于重写匹配到的路径。
//4.proxy_pass：该指令用于代理转发，它将匹配到的请求转发给位于后端的指令服务器。
@SpringBootApplication
public class TliasWebManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TliasWebManagementApplication.class, args);
	}

}
