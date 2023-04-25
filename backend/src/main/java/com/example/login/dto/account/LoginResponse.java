package com.example.login.dto.account;

import com.example.login.dao.user.User;
import lombok.Builder;



public record LoginResponse(User user,String token) {

}
