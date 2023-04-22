package com.example.login.config;

import com.example.login.dao.user.User;
import com.example.login.dao.user.UserRepo;
import com.example.login.exception.ResourceNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationConfig {

    private final UserRepo repo;

    public AuthenticationConfig(UserRepo repo) {
        this.repo = repo;
    }

    //載入userDetailService類(bean裡面是有真的userDetail的資料)
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) repo.findUserByUserName(username).orElseThrow(
                () -> new ResourceNotFoundException(User.class,"userName",username));
    }

    /**
     * like this:no lambda
     * return new UserDetailsService() {
     * public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     * return null;
     * }
     * };
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
