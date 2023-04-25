package com.example.login.dto.blog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Integer userId;
    private String userName;
    private String password;
    private String mobile;
    private String name;
}
