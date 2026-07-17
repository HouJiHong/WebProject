package com.hjh.service.impl;

import com.hjh.bean.JobOption;
import com.hjh.mapper.EmpMapper;
import com.hjh.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;


    /**
     * 获取员工职位数据
     */
    @Override
    public JobOption getEmpJobData() {
        //1.获取统计数据
        //这tm是一个list集合，里面的每一个元素是一个map集合，map里面只有两个键值对，pos=职位名称，num=职位数量
        List<Map<String, Object>> list = empMapper.countEmpJobData();
        //2.封装数据
        //获取list的stream流，将里面的每个元素（map集合,取名为dataMap）通过key获取map集合的值成为新流，最后添加到list集合中
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();

        return new JobOption(jobList, dataList);
    }


    /**
     * 统计员工性别*/
    //这个sql查询返回的结果也是多行多列，只不过前端不需要拆成单独的集合了，直接返回一个键值对集合
    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }
}
