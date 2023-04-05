package com.example.login.controller;

/**
 * <伺服器例外處理></伺服器例外處理>
 */

public class InternalServerException extends Exception{
    public InternalServerException(String message) {
        super(message);
    }
}
