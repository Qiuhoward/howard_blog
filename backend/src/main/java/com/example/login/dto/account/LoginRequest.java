package com.example.login.dto.account;

import lombok.Data;

@Data
public class LoginRequest {
    private String account;
    private String password;
}
