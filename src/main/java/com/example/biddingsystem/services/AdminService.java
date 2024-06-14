package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.AdminUsersResponseDto;
import com.example.biddingsystem.models.Category;

import java.util.List;

public interface AdminService {
    List<AdminUsersResponseDto> getAllUsers();
    void lockUser(String username);
    void unlockUser(String username);
}
