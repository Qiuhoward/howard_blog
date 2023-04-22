package com.example.login.dto.account;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * <註冊參數></註冊參數>
 */
@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class RegisterRequest {

    @Email(message = "account isn't be email format  ex:  ....@gmail.com")
    private String email;

    @Size(min = 8, max = 14,message = "min:8 ,max:14")
    private String password;

    @NotBlank(message = "name shouldn't be null")
    private String name;

    @Pattern(regexp = "^09\\d{8}$", message = "invalid mobile ex:09XX-XXX-XXX")
    private String mobile;

}
