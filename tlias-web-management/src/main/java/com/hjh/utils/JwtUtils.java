package com.hjh.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * 用于生成和解析令牌*/

public class JwtUtils {
    private static final String SECRET_KEY = "aGpo";  //密钥
    private static final long EXPIRATION_TIME = 12+60*60*1000;  //令牌过期时间

    //生成密钥
    public static String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }


    //解析密钥
    public static Map<String,Object> parseToken(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}


