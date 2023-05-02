package com.example.login.service;

import com.example.login.dao.user.Token;
import com.example.login.dao.user.TokenRepo;
import com.example.login.dto.blog.UserDto;
import com.example.login.exception.InternalServerException;
import com.example.login.dao.user.User;
import com.example.login.dao.user.UserRepo;
import com.example.login.dto.account.LoginRequest;
import com.example.login.dto.account.LoginResponse;
import com.example.login.dto.account.RegisterRequest;
import com.example.login.dto.account.RegisterResponse;
import com.example.login.exception.ResourceIsExistException;
import com.example.login.exception.ResourceNotFoundException;
import com.example.login.utils.BcryptUtils;
import com.example.login.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <認證服務></認證服務>
 * jwt在redis那邊存入多一倍時間利用這多一倍的鑰匙來判斷是否過期如果空了就自動產生新的
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;

    private final TokenRepo tokenRepo;
    private final JwtUtils jwtUtils;
    private final BcryptUtils bcryptUtils;
    private final ModelMapper mapper;

    public LoginResponse login(LoginRequest request) throws InternalServerException {
        log.info("進入登入 -> 參數為{}", request);

        //use java8 optional checked null
        var user = userRepo.findUserByUserName(request.getUserName()).orElseThrow(
                () -> new ResourceNotFoundException(User.class, "userName", request.getUserName()));

        var result = bcryptUtils.checkPassword(request.getPassword(), user.getPassword());

        if (!result) {
            throw new InternalServerException("帳號密碼錯誤");
        }
        log.info("成功登入");
        user.setLastTime(new Date());
        user = userRepo.save(user);
        var userDto = this.mapper.map(user, UserDto.class);
        userDto.setUsername(user.getUsername());
        jwtUtils.getTokenAndStoreToken(user,true);
        return new LoginResponse(userDto, jwtUtils.generateToken(user), jwtUtils.generateRefreshToken(user));
//        return new LoginResponse(this.mapper.map(user, UserDto.class), jwtUtils.generateToken(user));

    }

    public RegisterResponse register(RegisterRequest request) {
        log.info("進入註冊環節 -> 參數為{}", request);

        if (userRepo.findUserByUserName(request.getUserName()).isPresent()) {
            throw new ResourceIsExistException(User.class, "userName", request.getUserName());
        }
        if (!userRepo.findUserByMobile(request.getMobile()).isEmpty()) {
            throw new ResourceIsExistException(User.class, "phone", request.getMobile());
        }

        var encodePassword = jwtUtils.encode(request.getPassword());
        var user = new User(request);
        user.setPassword(encodePassword);
        user = userRepo.save(user);
        return jwtUtils.getTokenAndStoreToken(user,false);
    }

    //前端判定access token是否過期如果過期打refresh token這個接口戴上refresh Token
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return;
        var refreshToken = authHeader.substring(7);
        var user = userRepo.findUserByUserName(jwtUtils.extraUserName(refreshToken)).orElseThrow(
                () -> new ResourceNotFoundException(User.class, "userName", userRepo));

        if (jwtUtils.isTokenValid(refreshToken, user)) {
            var accessToken = jwtUtils.generateToken(user);
            //還沒弄
            var newRefreshToken = jwtUtils.generateRefreshToken(user);
            response.addCookie(createCookie("access_token", accessToken));
            response.addCookie(createCookie("refresh_token", newRefreshToken));
        }
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }
}
