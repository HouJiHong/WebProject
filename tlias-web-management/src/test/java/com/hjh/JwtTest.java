package com.hjh;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    /**
     * 生成Jwt令牌*/
    @Test
    public void testGenerateJwt(){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("username","admin");
        dataMap.put("name","管理员");

        //signWith(签名算法, 密钥：结果base64编码后)
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "aGpo")
                .addClaims(dataMap) //添加自定义数据
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) //设置过期时间 为一个小时后
                .compact(); //生成令牌

        System.out.println(jwt);
    }

    /**
     * 解析Jwt令牌*/
    @Test
    public void testParseJWT(){
        String token ="eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi566h55CG5ZGYIiwiZXhwIjoxNzg0MzU0ODE2fQ.WIMXk2o7cAT86eKKzErEusTm8kVIkUvRst1T_l0ZANQ";
        Claims data = Jwts.parser().setSigningKey("aGpo") //填写加密时的密钥
                .parseClaimsJws(token) //填写要解密的令牌
                .getBody(); //获取自定义的数据

        System.out.println(data);
    }
}
