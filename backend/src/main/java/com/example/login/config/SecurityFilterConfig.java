package com.example.login.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityFilterConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers("/auth/**")
                .permitAll()
                .requestMatchers("/swagger-ui/index.html#/**")
                .permitAll()
                .anyRequest().permitAll()
                .and().csrf().disable();

        return http.build();
    }
}








