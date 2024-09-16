package com.example.mynewapp.security;

import com.example.mynewapp.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    @Value("${auth.enabled}")
    private boolean enabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        String[] publicUris = {"/auth/registration","/auth/token"};
        boolean isPublicUri = Arrays.stream(publicUris).anyMatch(requestUri::startsWith);

        if (isPublicUri) {
            filterChain.doFilter(request,response);
        } else {
            if (!enabled) filterChain.doFilter(request, response);
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || authHeader.isBlank())
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            else if (!checkAuthorization(authHeader)) response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            else filterChain.doFilter(request, response);
        }
    }
    private boolean checkAuthorization(String auth){
        if (!auth.startsWith("Bearer ")) return false;
        String token = auth.substring(7);
        return tokenService.checkToken(token);
    }
}
