package com.example.login.controller;

import com.example.login.dto.LoginRequest;
import com.example.login.dto.LoginResponse;
import com.example.login.dto.RegisterRequest;
import com.example.login.dto.RegisterResponse;
import com.example.login.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <登入相關API>
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "auth")
public class AuthController {
    private final LoginService service;

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) throws InternalServerException {
        return new ResponseEntity<>(service.register(request), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws InternalServerException {
        return ResponseEntity.ok(service.login(request));
    }


}
