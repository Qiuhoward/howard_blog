package com.example.login.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String account;
    private String password;
}
