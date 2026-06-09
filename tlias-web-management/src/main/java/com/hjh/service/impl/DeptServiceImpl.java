package com.hjh.service.impl;

import com.hjh.bean.Dept;
import com.hjh.mapper.DeptMapper;
import com.hjh.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    //查询全部部门
    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    //删除部门
    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }

    //添加部门
    @Override
    public void add(Dept dept) {
        //1.补全基础属性 createTime,updateTime
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        //2.调用 mapper添加
        deptMapper.insert(dept);
    }

    //修改部门（查询要修改的部门）
    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    //修改部门
    @Override
    public void update(Dept dept) {
        //1.补全基础属性 updateTime
        dept.setUpdateTime(LocalDateTime.now());
        //2.调用 mapper修改
        deptMapper.update(dept);
    }
}
