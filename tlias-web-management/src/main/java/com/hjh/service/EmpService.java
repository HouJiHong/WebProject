package com.hjh.service;

import com.hjh.bean.Emp;
import com.hjh.bean.EmpQueryParam;
import com.hjh.bean.PageResult;

import java.time.LocalDate;

//由于员工经历表是员工表的附属，只需要再service中一起定义服务逻辑
public interface EmpService {
    //分页查询
    //PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);
    PageResult<Emp> page(EmpQueryParam empQueryParam);


    //添加员工信息
    void save(Emp emp);
}
