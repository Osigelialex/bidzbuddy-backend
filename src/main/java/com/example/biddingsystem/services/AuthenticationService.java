package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.LoginDto;
import com.example.biddingsystem.dto.RegisterDto;
import com.example.biddingsystem.dto.LoginResponseDto;
import com.example.biddingsystem.dto.UserDto;

public interface AuthenticationService {
    String register(RegisterDto registerDto);
    String verifyAccount(String token);
    String resendVerificationEmail(String email);
    LoginResponseDto login(LoginDto loginDto);
    UserDto getAuthenticatedUser();
}
