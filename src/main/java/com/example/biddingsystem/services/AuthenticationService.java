package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.LoginDto;
import com.example.biddingsystem.dto.RegisterDto;
import com.example.biddingsystem.dto.LoginResponseDto;
import com.example.biddingsystem.dto.UserDto;
import com.example.biddingsystem.models.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    void register(RegisterDto registerDto);
    LoginResponseDto login(LoginDto loginDto);
    UserDto getAuthenticatedUser();
}
