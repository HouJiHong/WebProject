package com.hjh.service.impl;

import com.hjh.bean.User;
import com.hjh.dao.UserDao;
import com.hjh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//@Component  // 将service对象交给Spring容器管理
@Service  //在service更适合使用（component的平替）
public class UserServiceImpl2 implements UserService {
    //private UserDao userDao = new UserDaoImpl();  不符合高内聚、低耦合
    @Autowired  //程序运行时，会自动查询该类型的bean对象，并赋值给该成员变量
    private UserDao userDao;

    @Override
    public List<User> getUserList() {
        //1．调用dao，获取用户数据
        List<String> lines = userDao.getUserList();
        //2，解析用户信息，封装为User对象->list集合
        List<User> userList = lines.stream().map(line -> {
            String[] parts = line.split(",");
            Integer id = Integer.parseInt(parts[0]);
            String username = parts[1];
            String password = parts[2];
            String name = parts[3];
            Integer age = Integer.parseInt(parts[4]);
            LocalDateTime updateTime = LocalDateTime.parse(parts[5], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return new User(id+200, username, password, name, age, updateTime);//创建User对象
        }).toList();

        //3.返回数据(json）
        return userList;
    }
}
