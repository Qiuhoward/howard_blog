package com.example.login.utils;

import com.example.login.dao.user.Token;
import com.example.login.dao.user.TokenRepo;
import com.example.login.dao.user.User;
import com.example.login.dto.account.RegisterResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * <JWT 工具包></JWT>
 */
@Component
public class JwtUtils {

    @Value("${application.security.jwt.expiration}")
    private long expireTime;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpireTime;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    private final SignatureAlgorithm alg = SignatureAlgorithm.HS256;

    private final BCryptPasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;
    private final TokenRepo tokenRepo;


    public JwtUtils(BCryptPasswordEncoder passwordEncoder, StringRedisTemplate redisTemplate, TokenRepo tokenRepo) {
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;

        this.tokenRepo = tokenRepo;
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

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setIssuer("howard")
                .setSubject(user.getUsername())//帳號不是名字
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpireTime))
                .signWith(getKey(), alg)//鑰匙和演算法執行簽名
                .compact();
    }


    public RegisterResponse getTokenAndStoreToken(User user,boolean revoked) {
        var accessToken = generateToken(user);
        var refreshToken = generateRefreshToken(user);
        if(revoked){
            revokeAllUserTokens(user);
        }
        saveToken(user, accessToken);
        redisTemplate.opsForValue().set(user.getName(), refreshToken, 10, TimeUnit.MINUTES);
        return new RegisterResponse("傳送token", accessToken, refreshToken);

    }

    private Boolean isTokenExpired(String token) {
        return extraExpired(token).before(new Date());
    }  //在現在之前過期回傳true

    private Date extraExpired(String token) {
        return extraClaim(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String userName = extraUserName(token);
        return userName.equals(user.getUsername()) && !isTokenExpired(token);
    }

    public String extraUserName(String token) {
        return extraClaim(token, Claims::getSubject);
    }

    public <T> T extraClaim(String token, Function<Claims, T> function) {
        final Claims claims = extraAllClaim(token);
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

    /**
     * 加密為密文
     */
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }


    /**
     * 撤銷原來的token
     */
    private void revokeAllUserTokens(User user) {
        var validUserTokenList = tokenRepo.findTokenByUser(user);
        if (validUserTokenList.isEmpty())
            return;
        validUserTokenList.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokenList);
    }


    private void saveToken(User user, String token) {
        var accessToken = Token.builder()
                .user(user)
                .token(token)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepo.save(accessToken);
    }

}
