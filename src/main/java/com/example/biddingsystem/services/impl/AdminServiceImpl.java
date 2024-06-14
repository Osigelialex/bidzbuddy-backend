package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.AdminUsersResponseDto;
import com.example.biddingsystem.models.UserEntity;
import com.example.biddingsystem.repositories.UserRepository;
import com.example.biddingsystem.services.AdminService;
import com.example.biddingsystem.services.EmailService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final EmailService emailService;
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
    public void lockUser(String username) {

    }

    @Override
    public void unlockUser(String username) {

    }
}
