package com.hjh.service;

import com.hjh.bean.Emp;
import com.hjh.bean.EmpQueryParam;
import com.hjh.bean.PageResult;

import java.time.LocalDate;
import java.util.List;

//由于员工经历表是员工表的附属，只需要再service中一起定义服务逻辑
public interface EmpService {
    //分页查询
    //PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);
    PageResult<Emp> page(EmpQueryParam empQueryParam);


    //添加员工信息
    void save(Emp emp);

    //批量删除员工信息
    void delete(List<Integer> ids);

    //修改员工信息（查询回显）
    Emp getInfo(Integer id);

    //修改员工（保存信息）
    void update(Emp emp);
}
