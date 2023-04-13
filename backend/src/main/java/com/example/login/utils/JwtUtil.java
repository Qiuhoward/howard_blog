package com.example.login.utils;


import com.example.login.dao.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;


@Component
public class JwtUtil {

    private static final int expireTime = 1000 * 600;
    private static final SignatureAlgorithm alg = SignatureAlgorithm.HS256;
    private static final String secretKey = "E)H@McQfTjWnZq4t7w!z%C*F-JaNdRgU";

    /**
     * <產生token></產生token>
     * Issuer:發行人
     * Subject:帳號不是名字
     * issuedAt:發行時間
     * exp:過期時間
     * sign:戴上密鑰和演算法
     *
     * @param user 使用者
     * @return token
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .setIssuer("howard")
                .setSubject(user.getAccount())//帳號不是名字
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getKey(), alg)//鑰匙和演算法執行簽名
                .compact();
    }

//
//    public String getToken() {
//        return ;
//    }

    /**
     * secretKey字串經過base64解密成bytes再轉成key
     */
    public Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
