package com.example.login.dto;

import com.example.login.dao.User;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LoginResponse {
    private User user;
}
