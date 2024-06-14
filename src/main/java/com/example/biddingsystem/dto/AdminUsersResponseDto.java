package com.example.biddingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUsersResponseDto {
    private long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String role;
    private LocalDateTime lastLogin;
}
