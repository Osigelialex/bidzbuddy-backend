package com.example.biddingsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationPoint implements AuthenticationEntryPoint {
    Logger logger = LoggerFactory.getLogger(AuthenticationPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, String> responseBody = new HashMap<>();
        String message;
        switch (authException) {
            case BadCredentialsException badCredentialsException -> message = "Invalid username or password";
            case DisabledException disabledException -> message = "Account is disabled";
            case CredentialsExpiredException credentialsExpiredException -> message = "Password has expired";
            case AuthenticationCredentialsNotFoundException authenticationCredentialsNotFoundException ->
                    message = "Authentication credentials not found";
            case InsufficientAuthenticationException insufficientAuthenticationException ->
                    message = "Insufficient authentication";
            case SessionAuthenticationException sessionAuthenticationException ->
                    message = "Session authentication failed";
            case ProviderNotFoundException providerNotFoundException -> message = "Authentication provider not found";
            case AuthenticationServiceException authenticationServiceException ->
                    message = "Authentication service exception";
            case null, default -> {
                logger.error("Authentication error: ", authException);
                message = "Authentication error";
            }
        }

        responseBody.put("error", "Unauthorized");
        responseBody.put("message", message);
        responseBody.put("timestamp", String.valueOf(System.currentTimeMillis()));

        logger.warn("Authentication failed: {}", message);
        responseBody.put("message", message);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }
}
