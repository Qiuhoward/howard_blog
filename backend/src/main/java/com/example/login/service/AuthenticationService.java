package com.example.login.service;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <認證服務></認證服務>
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
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
        var userDto=this.mapper.map(user, UserDto.class);
        userDto.setUserName(user.getUsername()); //原始碼命名沒有駝峰式造成吃不到userName變為null

        return new LoginResponse(userDto, jwtUtils.generateToken(user));
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

        return jwtUtils.getTokenAndStoreRedis(user);
    }


}
