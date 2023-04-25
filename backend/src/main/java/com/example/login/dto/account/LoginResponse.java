package com.example.login.dto.account;

import com.example.login.dao.user.User;
import com.example.login.dto.blog.UserDto;
import lombok.Builder;



public record LoginResponse(UserDto userDto, String token) {

}
