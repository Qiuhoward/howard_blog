package com.example.login.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthControllerTest {

/**
 * Mockito.when( 對象.方法名() ).thenReturn( 自定義結果 )
 * 3A測試原則
 * Arrange 初始化目標物件、相依物件、方法參數、預期結果
 * Act 呼叫目標物件的方法
 * Assert 驗證是否符合預期
 */

    @Test
    void register() {
        Assertions.assertAll();
    }

    @Test
    void login() {
    }
}