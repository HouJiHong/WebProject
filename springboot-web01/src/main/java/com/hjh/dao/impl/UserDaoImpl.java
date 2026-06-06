package com.hjh.dao.impl;

import cn.hutool.core.io.IoUtil;
import com.hjh.dao.UserDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//@Component //将dao层对象交给Spring容器管理
@Repository//在dao更适合使用（component的平替）
public class UserDaoImpl implements UserDao {

    @Override
    public List<String> getUserList() {
        //1．加载并读取user.txt文件，获取用户数据
        //java和resources文件在编译后会放在同一class(类)文件下
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("user.txt");
        //使用hutool工具读取文件所有行
        ArrayList<String> lines = IoUtil.readLines(in, StandardCharsets.UTF_8, new ArrayList<>());
        return lines;
    }
}
