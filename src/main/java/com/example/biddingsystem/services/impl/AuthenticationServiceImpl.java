package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.LoginDto;
import com.example.biddingsystem.dto.LoginResponseDto;
import com.example.biddingsystem.dto.RegisterDto;
import com.example.biddingsystem.dto.UserDto;
import com.example.biddingsystem.enums.Role;
import com.example.biddingsystem.exceptions.ValidationException;
import com.example.biddingsystem.exceptions.BiddingUnauthorizedException;
import com.example.biddingsystem.exceptions.DataConflictException;
import com.example.biddingsystem.models.UserEntity;
import com.example.biddingsystem.repositories.UserRepository;
import com.example.biddingsystem.security.JwtService;
import com.example.biddingsystem.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @Override
    public void register(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            throw new DataConflictException("Email already exists");
        }
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new DataConflictException("Username already exists");
        }
        try {
            Role.valueOf(registerDto.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Role must be ADMIN, SELLER or BUYER");
        }

        UserEntity user = new UserEntity();
        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setRole(Role.valueOf(registerDto.getRole()));
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        userRepository.save(user);
    }

    @Override
    public LoginResponseDto login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );

            UserEntity userEntity = userRepository.findByUsername(authentication.getName()).orElseThrow();
            String token = jwtService.generateToken(userEntity);
            UserDto userDto = modelMapper.map(userEntity, UserDto.class);
            return new LoginResponseDto(userDto, token);
        } catch (AuthenticationException e) {
            throw new BiddingUnauthorizedException("Invalid username or password");
        } catch (NoSuchElementException e) {
            throw new BiddingUnauthorizedException("User not found");
        }
    }
}