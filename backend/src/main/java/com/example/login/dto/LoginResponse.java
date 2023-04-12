package com.example.login.dto;

import com.example.login.dao.User;
import lombok.Builder;



@Builder
public record LoginResponse(User user) {

}
