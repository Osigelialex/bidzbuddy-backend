package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.AdminUsersResponseDto;
import com.example.biddingsystem.models.Category;

import java.util.List;

public interface AdminService {
    List<AdminUsersResponseDto> getAllUsers();
    void deleteUser(String username);
    void updateNotifications(String username, Integer notifications);
    void updateUsername(String username, String newUsername);
    void updateFirstname(String username, String firstname);
    void updateLastname(String username, String lastname);
    void updateRole(String username, String role);
    Category createCategory(Category category);
    Category updateCategoryById(Long categoryId, Category category);
    void deleteCategoryById(Long categoryId);
    void closeBidding(Long productId);
    void reopenBidding(Long productId);
}
