package com.Hjh;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class UserService {
    public Integer getAge(String idCard) {
        if(idCard== null|| idCard.length() != 18){
            throw new RuntimeException("请输入正确的身份证号码");
        }
        String birthday = idCard.substring(6, 14);
        LocalDate Parse = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return Period.between(Parse, LocalDate.now()).getYears();
    }

    public String  getGender(String idCard) {
        if(idCard== null|| idCard.length() != 18){
            throw new RuntimeException("请输入正确的身份证号码");
        }

        return Integer.parseInt(idCard.substring(16, 17))%2==1?"男":"女";
    }

}
