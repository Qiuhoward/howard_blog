package com.example.login.controller;

import com.example.login.dto.account.LoginRequest;
import com.example.login.dto.account.LoginResponse;
import com.example.login.dto.account.RegisterRequest;
import com.example.login.dto.account.RegisterResponse;
import com.example.login.exception.InternalServerException;
import com.example.login.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 * <登入相關API>
 */
@RequiredArgsConstructor
@RestController
@Tag(name = "註冊登入(Auth)")
@RequestMapping(value = "auth")
public class AuthController {
    private final AuthenticationService service;



    @PostMapping( "/register" )
    @Operation(summary = "註冊")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request){
        return new ResponseEntity<>(service.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "登入")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request ) throws InternalServerException, IOException {

        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "刷新token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        service.refreshToken(request,response);
    }



}
