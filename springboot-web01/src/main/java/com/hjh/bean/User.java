package com.hjh.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
//在创建模块的时候勾选lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //直接用包装类，可以防止原本数据为0
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer age;
    private LocalDateTime updateTime;
}
