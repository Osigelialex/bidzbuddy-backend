package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.*;
import com.example.biddingsystem.services.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(value = "http://localhost:5173")
@AllArgsConstructor
public class AuthController {
    private AuthenticationService authenticationService;
    private HttpServletResponse response;

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto) {
        authenticationService.register(registerDto);
        return new ResponseEntity<>("User signed up successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginDto) {
        LoginResponseDto loginResponseDto = authenticationService.login(loginDto);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getAuthenticatedUser() {
        UserDto userDto = authenticationService.getAuthenticatedUser();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
