package com.hjh.mapper;

import com.hjh.bean.EmpExpr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *员工工作经历
 */
@Mapper
public interface EmpExprMapper {
    //批量保存员工工作经历,由于插入的数据是动态的，需要使用动态sql，且sql语句复杂需要xml映射
    void insertBatch(List<EmpExpr> exprList);
}
