package com.example.login.service;

import com.example.login.controller.InternalServerException;
import com.example.login.dao.User;
import com.example.login.dao.UserRepo;
import com.example.login.dto.LoginRequest;
import com.example.login.dto.LoginResponse;
import com.example.login.dto.RegisterRequest;
import com.example.login.dto.RegisterResponse;
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
            throw new InternalServerException("帳號或密碼錯誤");
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
            throw new InternalServerException("密碼輸入不一致");
        }
        if (!userRepo.findUserByAccount(request.getAccount()).isEmpty()) {
            throw new InternalServerException("帳號已存在");
        }
        if (!userRepo.findUserByMobile(request.getMobile()).isEmpty()) {
            throw new InternalServerException("手機號碼已註冊過");
        }

        encode(request.getPassword1());

        var user=new User(request);
        user.setPassword(encode(request.getPassword1()));
        userRepo.save(user);
        return RegisterResponse.builder()
                .status("新增成功")
                .build();
    }

    public String encode(String password) {
      return  passwordEncoder.encode(password);
    }

}
