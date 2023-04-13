package com.example.login.service;

import com.example.login.exception.EventError;
import com.example.login.exception.InternalServerException;
import com.example.login.dao.entities.User;
import com.example.login.dao.repos.UserRepo;
import com.example.login.dto.account.LoginRequest;
import com.example.login.dto.account.LoginResponse;
import com.example.login.dto.account.RegisterRequest;
import com.example.login.dto.account.RegisterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    public LoginResponse login(LoginRequest request) throws InternalServerException {
        log.info("進入登入 -> 參數為{}", request);
        if (userRepo.findUserByAccount(request.getAccount()).isEmpty()) {
            throw new InternalServerException("帳號不存在");
        }
        var user =
                userRepo.findUserByAccountAndPassword(request.getAccount(), request.getPassword());

        if (user.isEmpty()) {
            throw new InternalServerException(EventError.ACCOUNT_PASSWORD_ERROR.toString());
        }
        user.get().setLastTime(new Date());
        userRepo.save(user.get());

        return LoginResponse
                .builder()
                .user(user.get())
                .build();
    }

    public RegisterResponse register(RegisterRequest request) throws InternalServerException {
        log.info("進入註冊環節 -> 參數為{}", request);

        if (!Objects.equals(request.getPassword1(), request.getPassword2())) {
            throw new InternalServerException(EventError.PASSWORD_IS_NOT_SAME.toString());
        }
        if (!userRepo.findUserByAccount(request.getAccount()).isEmpty()) {
            throw new InternalServerException(EventError.ACCOUNT_IS_EXIST.toString());
        }
        if (!userRepo.findUserByMobile(request.getMobile()).isEmpty()) {
            throw new InternalServerException(EventError.PHONE_IS_SIGN_UP.toString());
        }

        encode(request.getPassword1());

        var user=new User(request);
        var status="新增成功";

        user.setPassword(encode(request.getPassword1()));
        userRepo.save(user);
        return RegisterResponse.builder()
                .status(status)
                .build();
    }

    public String encode(String password) {
      return  passwordEncoder.encode(password);
    }

}
