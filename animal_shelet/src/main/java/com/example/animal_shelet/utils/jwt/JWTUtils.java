package com.example.animal_shelet.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    /**
     * 生成token  header.payload.singature
     */
    private static final String SING = "XIAOSHUANG";

    public static String getToken(Map<String, String> map) {

        Calendar instance = Calendar.getInstance();
        // 默认7天过期
        instance.add(Calendar.DATE, 7);

        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        // payload
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        String token = builder.withExpiresAt(instance.getTime())  //指定令牌过期时间
                .sign(Algorithm.HMAC256(SING));  // sign
        return token;
    }

    /**
     * 验证token合法性
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }

    /**
     * 获取token信息方法
     */
    public static Map<String, String> getTokenInfo(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);

        // 获取声明
        Map<String, String> claims = new HashMap<>();
        claims.put("username", verify.getClaim("username").asString());
        claims.put("password", verify.getClaim("password").asString());
        claims.put("userId", verify.getClaim("userId").asString());
        claims.put("Email", verify.getClaim("Email").asString());
        claims.put("phone", verify.getClaim("phone").asString());
        claims.put("roleId", verify.getClaim("roleId").asString());
        return claims;
    }

}

