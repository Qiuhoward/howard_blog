package com.example.login.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String userName;
    private String password;
}
