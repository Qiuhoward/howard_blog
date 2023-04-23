package com.example.login.exception;


import lombok.Getter;
import lombok.Setter;

/**
 * <api錯誤回傳封包></api錯誤回傳封包>
 */
@Getter
@Setter
public class ApiResponse {

    public final String Code;
    public final Boolean status;


    public ApiResponse(String code, Boolean status) {
        this.Code = code;
        this.status = status;
    }
}
