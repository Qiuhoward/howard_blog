package com.example.login.dto.account;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
