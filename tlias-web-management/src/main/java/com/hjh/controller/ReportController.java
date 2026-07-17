package com.hjh.controller;


import com.hjh.bean.JobOption;
import com.hjh.bean.Result;
import com.hjh.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 用于员工信息统计报表*/

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 统计员工职位信息*/
    @GetMapping("/empJobData")
    public Result getEmpJobData(){
        log.info("统计员工职位信息");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    /**
     * 统计员工性别*/
    @GetMapping("/empGenderData")
    public Result getEmpGenderData(){
        log.info("统计员工性别信息");
        List<Map<String, Object>> genderList = reportService.getEmpGenderData();
        return Result.success(genderList);
    }
}
