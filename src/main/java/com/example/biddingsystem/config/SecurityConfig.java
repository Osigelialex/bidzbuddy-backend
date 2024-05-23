package com.example.biddingsystem.config;

import com.example.biddingsystem.security.AuthenticationPoint;
import com.example.biddingsystem.security.JwtAuthenticationFilter;
import com.example.biddingsystem.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;

@Controller
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private UserDetailsServiceImpl userDetailsServiceImpl;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private AuthenticationPoint authenticationPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->req
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/unprotected").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/products").hasAuthority("SELLER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/products").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasAuthority("SELLER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/categories/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/users").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/dashboard").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/bids/all").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/bids/place").hasAuthority("BUYER")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/bids/close/**")
                        .hasAnyAuthority("SELLER", "ADMIN")
                        .requestMatchers("/api/v1/bids/winner/**").hasAuthority("SELLER")
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsServiceImpl)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationPoint))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    private static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
