package com.example.biddingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String role;
    private List<NotificationDto> notifications;
}
