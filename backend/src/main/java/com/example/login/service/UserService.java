package com.example.login.service;

import com.example.login.dao.user.User;
import com.example.login.dto.account.LoginRequest;
import com.example.login.dto.blog.UserDto;

import java.util.List;

public interface UserService {

    UserDto editUserInfo(String name,String mobile,Integer userId);
    UserDto editUserPassword(String password,Integer userId);
    List<UserDto> findAllUser();
    void deleteUser(Integer userId);

    List<UserDto> findUserByTitle(String keyword);
}
