package com.example.login.filter;


import com.example.login.dao.user.User;
import com.example.login.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * <過濾api有無token></過濾>
 */

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        log.info("進行過濾");
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;
        log.info("header:{}", authHeader);
        if (authHeader == null || authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userName = jwtUtil.extraUserName(jwt);


        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var user = this.userDetailsService.loadUserByUsername(userName);
            if (jwtUtil.isTokenValid(jwt, user)) {
                //UsernamePasswordAuthenticationToken的作用就是代表一個被驗證的請求對象，並在通過驗證後裝載使用者完整資訊的對象，
                // 這些資訊包括使用者名稱(username)，密碼(password)，權限(authorities)等。
                var authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}
