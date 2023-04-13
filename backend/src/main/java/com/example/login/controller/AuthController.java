package com.example.login.controller;

import com.example.login.dto.account.LoginRequest;
import com.example.login.dto.account.LoginResponse;
import com.example.login.dto.account.RegisterRequest;
import com.example.login.dto.account.RegisterResponse;
import com.example.login.exception.InternalServerException;
import com.example.login.service.AuthenticationService;
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
    private final AuthenticationService service;

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) throws InternalServerException {
        return new ResponseEntity<>(service.register(request), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws InternalServerException {
        return ResponseEntity.ok(service.login(request));
    }


}
