package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.AdminUsersResponseDto;

import java.util.List;

public interface UserService {
    List<AdminUsersResponseDto> getAllUsers();
    void deleteUser(String username);
    void updateNotifications(String username, Integer notifications);
    void updateUsername(String username, String newUsername);
    void updateFirstname(String username, String firstname);
    void updateLastname(String username, String lastname);
    void updateRole(String username, String role);
}
