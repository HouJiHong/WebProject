package com.hjh.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用于封装条形图的职位数据
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOption {
    private List jobList; //职位
    private List dataList;//职位对应的数据
}
