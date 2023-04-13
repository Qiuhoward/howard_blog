package com.example.login.dto.account;

import lombok.Builder;


@Builder
public record RegisterResponse (String status,String token){

}
