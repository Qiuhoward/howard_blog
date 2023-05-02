package com.example.login.dto.account;

import com.example.login.dto.blog.UserDto;



public record LoginResponse(UserDto userDto, String accessToken,String refreshToken) {

}
