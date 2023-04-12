package com.example.login.controller;

public enum EventError {

    ACCOUNT_IS_EXIST("帳號不存在","4000"),
    ACCOUNT_PASSWORD_ERROR("帳號或密碼錯誤","4001"),
    PASSWORD_IS_NOT_SAME("密碼輸入不一致","4002"),
    PHONE_IS_SIGN_UP("手機號碼已註冊過","4003");



    public final String Code;
    public final String Description;

    EventError(String code, String description) {
        this.Code = code;
        this.Description = description;
    }


}
