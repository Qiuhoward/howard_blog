package com.example.login.utils;

import com.example.login.dao.user.User;
import com.example.login.dto.account.RegisterResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final int expireTime = 1000 * 600;
    private static final SignatureAlgorithm alg = SignatureAlgorithm.HS256;
    private static final String secretKey = "E)H@McQfTjWnZq4t7w!z%C*F-JaNdRgU";
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;


    public JwtUtil(PasswordEncoder passwordEncoder, StringRedisTemplate redisTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;

    }

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
                .setSubject(user.getUsername())//帳號不是名字
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getKey(), alg)//鑰匙和演算法執行簽名
                .compact();
    }


    public RegisterResponse getTokenAndStoreRedis(User user) {
        var token = generateToken(user);
        redisTemplate.opsForValue().set(user.getName(), token);
        redisTemplate.expire(token, 5, TimeUnit.MINUTES);
        return RegisterResponse.builder()
                .token(token)
                .status("傳送token")
                .build();
    }
    private Boolean isTokenExpired(String token){
        return extraExpired(token).before(new Date());//在現在之前過期回傳true
    }

    private Date extraExpired(String token) {
        return extraClaim(token,Claims::getExpiration);
    }
    public boolean isTokenValid(String token,UserDetails user){
        final String userName= extraUserName(token);
        return userName.equals(user.getUsername())&& !isTokenExpired(token);
    }                                                                                                         {

    }
    public String extraUserName(String token){
        return extraClaim(token,Claims::getSubject);
    }
    public <T>T extraClaim(String token,Function<Claims,T>function){
        final Claims claims=extraAllClaim(token);
        return function.apply(claims);
    }
    public Claims extraAllClaim(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }



    /**
     * secretKey字串經過base64解密成bytes再轉成key
     */
    public Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
