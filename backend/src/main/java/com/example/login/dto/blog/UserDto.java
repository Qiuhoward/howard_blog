package com.example.login.dto.blog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <用戶封包></用戶封包>
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Integer userId;
    private String username;
    private String password;
    private String mobile;
    private String name;
}
