package com.example.login.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping(value = "test")
public class TestController {

    @PostMapping(value = "/get_name")
    public ResponseEntity<String> getName(@RequestParam String name) {
        log.info("{}你好阿",name);
        return new ResponseEntity<>(name + "您好api串接成功", HttpStatus.ACCEPTED);
    }
}
