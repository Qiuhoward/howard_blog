package com.example.login.service;

import com.example.login.exception.EventError;
import com.example.login.exception.InternalServerException;
import com.example.login.dao.user.User;
import com.example.login.dao.user.UserRepo;
import com.example.login.dto.account.LoginRequest;
import com.example.login.dto.account.LoginResponse;
import com.example.login.dto.account.RegisterRequest;
import com.example.login.dto.account.RegisterResponse;
import com.example.login.utils.BcryptUtils;
import com.example.login.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final JwtUtils jwtUtils;
    private final BcryptUtils bcryptUtils;

    public LoginResponse login(LoginRequest request) throws InternalServerException {
        log.info("進入登入 -> 參數為{}", request);

        //use java8 optional checked null
      var user=  userRepo.findUserByUserName(request.getEmail()).orElseThrow(
                () -> new InternalServerException(EventError.ACCOUNT_IS_NOT_EXIST.toString())
        );

        var result=bcryptUtils.checkPassword(request.getPassword(),user.getPassword());

        if(!result){
            throw new InternalServerException(EventError.ACCOUNT_PASSWORD_ERROR.toString());
        }
        log.info("成功登入");
        user.setLastTime(new Date());
        userRepo.save(user);

        return LoginResponse
                .builder()
                .token(jwtUtils.generateToken(user))
                .user(user)
                .build();
    }

    public RegisterResponse register(RegisterRequest request) throws InternalServerException {
        log.info("進入註冊環節 -> 參數為{}", request);

        if (userRepo.findUserByUserName(request.getEmail()).isPresent()) {
            throw new InternalServerException(EventError.ACCOUNT_IS_EXIST.toString());
        }
        if (!userRepo.findUserByMobile(request.getMobile()).isEmpty()) {
            throw new InternalServerException(EventError.PHONE_IS_SIGN_UP.toString());
        }

        var encodePassword = jwtUtils.encode(request.getPassword());
        var user = new User(request);

        user.setPassword(encodePassword);
        userRepo.save(user);


        return jwtUtils.getTokenAndStoreRedis(user);
    }


}
