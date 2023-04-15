package com.example.login.config;

import com.example.login.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class SecurityFilterConfig {
    private final AuthenticationProvider provider;
    private final JwtFilter jwtFilter;

    private final String[] AUTH_PERMISSION_LIST = {
            "/auth/**",
            "/v3/api-docs/**", // spring security+swagger整合的坑(換上version 3)
            "/swagger-ui/**",//訪問swagger地址
            "/test/**"
    };

    public SecurityFilterConfig(AuthenticationProvider provider, JwtFilter jwtFilter) {
        this.provider = provider;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests().requestMatchers(AUTH_PERMISSION_LIST)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(provider)
                //UsernamePasswordAuthenticationFilter：
                // 用于处理基于表单的登录请求，从表单中获取用户名和密码。 默认情况下处理来自“/login”的请求。
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                .and()
                .cors().and().csrf().disable();

        return http.build();
    }
}








