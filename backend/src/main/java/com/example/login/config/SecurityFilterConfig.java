package com.example.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityFilterConfig {


    private final String[] AUTH_PERMISSION_LIST = {
            "/auth/**",
            "/v3/api-docs/**", // spring security+swagger整合的坑(換上version 3)
            "/swagger-ui/**",//訪問swagger地址
            "/post/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests().requestMatchers(AUTH_PERMISSION_LIST)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .and()
                .cors().and().csrf().disable();

        return http.build();
    }
}








