package com.hjh.mapper;

import com.hjh.bean.Dept;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
//数据封装 (由于数据库和java的命名规范不一样，但自动封装要求名字一样)
//实体类属性名和数据库表查询返回的字段名一致，mybatis会自动封装。
//如果实体类属性名和数据库表查询返回的字段名不一致，不能自动封装。
//方法一：手动封装：通过@Results和@Result来手动封装
//方法二：起别名(直接在sql语句中添加属性名)
//方法三：开启驼峰命名 配置：如果字段名与属性名符合驼峰命名规则，mybatis会自动通过驼峰命名规则映射。（推荐）
//                          也就是数据库的字段名连接单词用 下划线 连接，java的属性名用 驼峰 命名，才可以用
@Mapper
public interface DeptMapper {
    //查询全部部门
    /*//方法一
    @Results({//一个result对应一个字段，column对应数据库字段，property对应实体类属性
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })*/
    /*//方法二
    @Select("select id, name, create_time createTime, update_time updateTime from dept order by update_time desc ")*/

    @Select("select id, name, create_time , update_time from dept order by update_time desc ")
    List<Dept> findAll();


    //删除部门
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    //添加部门
    @Insert("insert into dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime})")
    void insert(Dept dept);

    //修改部门（查询要修改的部门）
    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept getById(Integer id);

    //修改部门
    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
