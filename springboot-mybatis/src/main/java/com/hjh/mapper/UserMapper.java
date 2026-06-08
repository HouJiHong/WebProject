package com.hjh.mapper;
//MyBatis是款的持久层（springboot的数据层）框架,是对jdbc的封装
//mapper对应springboot的repository（dao）

import com.hjh.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//准备工作：
//1.创建SpringBoot工程、引入Mybatis相关依赖
//2.准备数据库表user、实体类user
//3.配置Mybatis（在application.properties中数据库连接信息）
//编写Mybatis程序：编写Mybatis的持久层接口XxxMapper，定义SQL(注解/XML）

//#{...} 占位符.执行时，会将#{}替换为？,生成预编译SQL          参数值传递                 安全、性能高
//${...} 拼接符.直接将参数拼接在SQL语句中,存在SQL注入问题       表名、字段名动态设置时使用   不安全、性能低
//传统sql执行和预编译sql的区别：
//传统：
//SQL语句 → 语法解析 → 语义分析 → 生成执行计划 → 执行 → 返回结果
//预编译sql执行：
//阶段1：预编译
//SQL模板 → 语法解析 → 语义分析 → 生成执行计划 → 缓存执行计划
//阶段2：多次执行
//传入参数 → 使用缓存的执行计划 → 执行 → 返回结果

@Mapper//（控制反转）添加Mybatis的持久层接口，应用程序在运行时，会自动创建该接口的实现类对象，并注入到Spring容器中
public interface UserMapper {
    //由与在xml中已经定义了相同的findUser方法，只能存在一个，这里就注释删除了注解
    //@Select("select * from user")
    public List<User> findUser();//调用findUser方法会自动调用@Select注解的SQL语句，并返回结果到返回类型List<User>对象中

    @Delete("delete from user where id = #{id}")
    public Integer deleteById(Integer id);//调用deleteById方法会自动调用@Delete注解的SQL语句，将sql语句中的#{id}替换为？，
                                            //将模板与参数一起传给DBMS，并返回结果(运行时影响的行数)到返回类型Integer对象中


    //如果想要替换多个参数，法一：用对象
    @Insert("insert into user(username,password,name,age) values(#{username},#{password},#{name},#{age})")
                              //此为表中的字段                         此为User类中的属性
    public Integer insert(User user);

    @Update("update user set username = #{username},password = #{password},name = #{name},age = #{age} where id = #{id}")
    public Integer update(User user);

    //法二：如果不用对象，就必须要用@Param注解，用@Param注解给参数命名，这样参数和形参就一一对应，不然编译后形参是对应不到sql参数
            //或者项目使用的是springboot官方骨架，那么@Param注解可以省略
    @Select("select * from user where username = #{uname} and password = #{passwd}")
    public User findUserByUsernameAndPassword(@Param("uname") String username, @Param("passwd") String password);
    //由于返回值只有一条记录，可以用对象直接接
}
