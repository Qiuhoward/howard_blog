package com.example.login.dto.account;

import com.example.login.dao.entities.User;
import lombok.Builder;



@Builder
public record LoginResponse(User user,String token) {

}
