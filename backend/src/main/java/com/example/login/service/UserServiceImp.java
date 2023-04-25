package com.example.login.service;

import com.example.login.dao.user.User;
import com.example.login.dao.user.UserRepo;
import com.example.login.dto.blog.UserDto;
import com.example.login.exception.ResourceNotFoundException;
import com.example.login.utils.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;


@Log4j2
@Service
public class UserServiceImp implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private final JwtUtils jwtUtils;

    public UserServiceImp(UserRepo userRepo, ModelMapper mapper, JwtUtils jwtUtils) {
        this.userRepo = userRepo;
        this.mapper = mapper;
        this.jwtUtils = jwtUtils;
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);//MAPPER映射問題
    }

    @Override
    public UserDto editUserInfo(String name, String mobile, Integer userId) {
        log.info("編輯用戶個人資訊 名字:{},手機號碼:{},使用者ID:{}", name, mobile, userId);
        var user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(User.class, "userId", userId));

        user.setMobile(mobile);
        user.setName(name);
        user = userRepo.save(user);
        return this.mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto editUserPassword(String password, Integer userId) {
        log.info("編輯用戶個人密碼 新密碼:{},使用者ID:{}", password, userId);
        var user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(User.class, "userId", userId));

        var bcryptPassword = jwtUtils.encode(password);
        user.setPassword(bcryptPassword);
        user = userRepo.save(user);

        return this.mapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAllUser() {
        return userRepo.findAll()
                .stream()
                .map((user) -> this.mapper.map(user, UserDto.class)).toList();
    }

    @Override
    public void deleteUser(Integer userId) {
        var user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(User.class, "userId", userId));

        userRepo.delete(user);
    }

    @Override
    public List<UserDto> findUserByTitle(String keyword) {
        log.info("關鍵字查詢用戶:{}", keyword);
        var userList = userRepo.findUserByNameContaining(keyword);

        return userList
                .stream()
                .map((user) -> this.mapper.map(user, UserDto.class))
                .toList();
    }
}
