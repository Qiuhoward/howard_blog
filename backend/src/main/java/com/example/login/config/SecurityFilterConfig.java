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
import org.springframework.web.cors.CorsConfiguration;

/**
 * <過濾器配置檔></過濾器配置檔>
 */
@EnableWebSecurity
@Configuration
public class SecurityFilterConfig {
    private final AuthenticationProvider provider;
    private final JwtFilter jwtFilter;
    private final String[] AUTH_PERMISSION_LIST = {
            "/auth/**",
            "/v3/api-docs/**", // spring security+swagger整合的坑(換上version 3)
            "/swagger-ui/**",//訪問swagger地址
            "/test/**",
            "/comment/**",
            "/post/**",
            "/category/**"

    };

    public SecurityFilterConfig(AuthenticationProvider provider, JwtFilter jwtFilter) {
        this.provider = provider;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("Content-Type");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        config.setAllowCredentials(false);

        http.authorizeHttpRequests().requestMatchers(AUTH_PERMISSION_LIST)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(provider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .cors().disable()
                .cors().configurationSource((option) -> config)// return config;
//              .oauth2Login()
                .and()
                .csrf().disable();

        return http.build();
    }

}








