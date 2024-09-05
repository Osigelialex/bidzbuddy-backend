package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.*;
import com.example.biddingsystem.services.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Authentication", description = "Authentication functionalities")
public class AuthController {
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto) {
        return new ResponseEntity<>(authenticationService.register(registerDto), HttpStatus.OK);
    }

    @PostMapping("/resend")
    public ResponseEntity<String> resendVerificationEmail(@RequestParam String email) {
        return new ResponseEntity<>(authenticationService.resendVerificationEmail(email), HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam String token) {
        return new ResponseEntity<>(authenticationService.verifyAccount(token), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginDto) {
        LoginResponseDto loginResponseDto = authenticationService.login(loginDto);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/change-password")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) {
        authenticationService.changePassword(changePasswordDto.getOldPassword(), changePasswordDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserDto> getAuthenticatedUser() {
        UserDto userDto = authenticationService.getAuthenticatedUser();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
