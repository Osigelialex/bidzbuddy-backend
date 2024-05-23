package com.example.biddingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUsersResponseDto {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String role;
}
