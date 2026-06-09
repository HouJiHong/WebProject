package com.hjh.service;


import com.hjh.bean.Dept;

import java.util.List;

public interface DeptService {
    //查询全部部门
    List<Dept> findAll();

    //删除部门
    void deleteById(Integer id);

    //添加部门
    void add(Dept dept);

    //修改部门（查询要修改的部门）
    Dept getById(Integer id);

    //修改部门
    void update(Dept dept);
}
