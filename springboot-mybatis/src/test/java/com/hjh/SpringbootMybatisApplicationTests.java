package com.hjh;

import com.hjh.bean.User;
import com.hjh.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

//数据库连接池是个容器，负责分配、管理数据库连接（Connection）。
//它允许应用程序重复使用一个现有的数据库连接，而不是再重新建立一个。
//释放空闲时间超过最大空闲时间的连接，来避免因为没有释放连接而引起的数据库连接遗漏。
//优势：
//1．资源重用
//2．提升系统响应速度
//3．避免数据库连接遗漏

//标准接口：DataSource
//官方（sun)提供的数据库连接池接口，由第三方组织实现此接口。
//功能：获取连接 Connection getConnection() throws SQLException;
//常见产品：
//Druid			Hikari(SpringBoot默认)

//切换数据库连接池：
//1.修改pom.xml文件，添加依赖
//<dependency>
//<groupId>com.alibaba</groupId>
//<artifactId>druid-spring-boot-starter</artifactId>
//<version>1.2.19</version>
//</dependency>
//2.配置application.properties
//添加spring.datasource.type=com.alibaba.druid.pool.DruidDatasource


@SpringBootTest//springboot单元测试的注解，当测试类启动时，会自动启动springboot程序
class SpringbootMybatisApplicationTests {
	@Autowired
	private UserMapper userMapper;

	@Test
	public void testFindUser(){
		List<User> users = userMapper.findUser();
		for (User user : users) {
			System.out.println(user);
		}
	}

	@Test
	public void testDeleteById(){
		Integer i = userMapper.deleteById(2);
		System.out.println(i);
	}

	@Test
	public void testInsert(){
		User user = new User(null, "hjh", "10086", "神灵", 18);
		Integer i = userMapper.insert(user);
		System.out.println(i);
	}

	@Test
	public void testUpdate(){
		User user = new User(4, "xiaoqiao", "10086", "小乔", 18);
		Integer i = userMapper.update(user);
		System.out.println(i);
	}

	@Test
	public void testFindBy(){
		User user = userMapper.findUserByUsernameAndPassword("xiaoqiao", "10086");
		System.out.println("用户id："+user.getId()+"用户名："+user.getUsername()+"密码："
				+user.getPassword()+"姓名："+user.getName()+"年龄："+user.getAge());
	}

}
