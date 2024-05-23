package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.AdminUsersResponseDto;
import com.example.biddingsystem.exceptions.ResourceNotFoundException;
import com.example.biddingsystem.models.UserEntity;
import com.example.biddingsystem.repositories.UserRepository;
import com.example.biddingsystem.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

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
}
