package com.ltl.ssocenter.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类（hutool）
 */
public class JwtUtil {

    /**
     * Description: 生成一个jwt字符串
     *
     * @param data    jwt传输的数据
     * @param secretKey  秘钥
     * @param timeOut 超时时间（单位s）
     * @return java.lang.String
     */
    public static String encode(String data, String secretKey, int timeOut) {
        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.SECOND, timeOut);

        Map<String,Object> payload = new HashMap<>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //载荷
        payload.put("data", data);

        return JWTUtil.createToken(payload, secretKey.getBytes());
    }

    /**
     * Description: 解密jwt
     *
     * @param token  token
     * @param secretKey 秘钥
     * @return cn.hutool.json.JSONObject
     */
    public static JSONObject decode(String token, String secretKey) throws Exception {
        if (token == null || token.length() == 0) {
            throw new Exception("token为空...");
        }
        JWT jwt = JWTUtil.parseToken(token);
        boolean verifyKey = jwt.setKey(secretKey.getBytes()).verify();
        boolean verifyTime = jwt.validate(0);
        // 秘钥验证和过期时间验证
        if (! (verifyKey && verifyTime)){
            throw new Exception("签名验证失败...");
        }

        return jwt.getPayloads();
    }
}