package com.example.login.exception;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {


    public final String Code;
    public final Boolean status;


    public ApiResponse(String code,Boolean status) {
        this.Code = code;
        this.status = status;
    }
}
