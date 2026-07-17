package com.hjh.service;

import com.hjh.bean.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    //统计员工职位信息
    JobOption getEmpJobData();

    //统计员工性别
    List<Map<String, Object>> getEmpGenderData();
}
