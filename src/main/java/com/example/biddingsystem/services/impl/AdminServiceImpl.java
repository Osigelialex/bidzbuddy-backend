package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.AdminUsersResponseDto;
import com.example.biddingsystem.exceptions.DataConflictException;
import com.example.biddingsystem.exceptions.ResourceNotFoundException;
import com.example.biddingsystem.exceptions.ValidationException;
import com.example.biddingsystem.models.Category;
import com.example.biddingsystem.models.UserEntity;
import com.example.biddingsystem.repositories.CategoryRepository;
import com.example.biddingsystem.repositories.UserRepository;
import com.example.biddingsystem.services.AdminService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<AdminUsersResponseDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        if (users.isEmpty()) {
            return Collections.emptyList();
        }
        return users.stream().map(user -> modelMapper.map(user, AdminUsersResponseDto.class)).toList();
    }

    @Override
    public void deleteUser(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("No user associated with that username");
        }

        userRepository.delete(user.get());
    }

    @Override
    public void updateNotifications(String username, Integer notifications) {

    }

    @Override
    public void updateUsername(String username, String newUsername) {

    }

    @Override
    public void updateFirstname(String username, String firstname) {

    }

    @Override
    public void updateLastname(String username, String lastname) {

    }

    @Override
    public void updateRole(String username, String role) {

    }

    @Override
    public Category createCategory(Category category) {
        if (category.getName() == null) {
            throw new ValidationException("All fields are required");
        }

        if (categoryRepository.existsByName(category.getName())) {
            throw new DataConflictException("Category already exists");
        }

        categoryRepository.save(category);
        return category;
    }

    @Override
    public Category updateCategoryById(Long categoryId, Category category) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new ValidationException("Category not found");
        }

        if (category.getName() != null && category.getName().isEmpty()) {
            throw new ValidationException("Category name cannot be empty");
        }

        if (category.getName() != null && categoryRepository.existsByName(category.getName())) {
            throw new ValidationException("Category already exists");
        }

        Category updatedcategory = categoryOptional.get();

        if (category.getName() != null) updatedcategory.setName(category.getName());
        return categoryRepository.save(updatedcategory);
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new ValidationException("Category not found");
        }
        categoryRepository.delete(categoryOptional.get());
    }

    @Override
    public void closeBidding(Long productId) {

    }

    @Override
    public void reopenBidding(Long productId) {

    }
}
