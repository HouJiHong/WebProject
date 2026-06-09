package com.hjh.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpExpr {
    private Integer id;
    private Integer empId; //注意：此id是为了将员工经历信息与员工信息绑定，
                                // 因为添加员工信息和添加员工工作经历信息是通过两次提交
    private LocalDate begin;
    private LocalDate end;
    private String company;
    private String job;
}
