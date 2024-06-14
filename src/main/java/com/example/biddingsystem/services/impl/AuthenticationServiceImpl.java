package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.*;
import com.example.biddingsystem.enums.Role;
import com.example.biddingsystem.exceptions.ResourceNotFoundException;
import com.example.biddingsystem.exceptions.ValidationException;
import com.example.biddingsystem.exceptions.UnauthorizedException;
import com.example.biddingsystem.exceptions.DataConflictException;
import com.example.biddingsystem.models.ConfirmationToken;
import com.example.biddingsystem.models.UserEntity;
import com.example.biddingsystem.repositories.ConfirmationTokenRepository;
import com.example.biddingsystem.repositories.UserRepository;
import com.example.biddingsystem.security.JwtService;
import com.example.biddingsystem.services.AuthenticationService;
import com.example.biddingsystem.services.ConfirmationTokenService;
import com.example.biddingsystem.services.EmailService;
import com.example.biddingsystem.services.NotificationService;
import com.example.biddingsystem.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final EmailService emailService;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final NotificationService notificationService;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final SecurityUtils securityUtils;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    @Transactional
    public String register(RegisterDto registerDto) {
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

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setUserEntity(user);
        confirmationTokenService.createConfirmationToken(confirmationToken);

        String htmlContent = emailService.buildEmailConfirmationEmail(confirmationToken.getToken());
        emailService.sendEmail(user.getEmail(), "Email Confirmation", htmlContent);

        return registerDto.getEmail();
    }

    @Override
    public String verifyAccount(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token);
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ValidationException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ValidationException("Token expired");
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        UserEntity user = confirmationToken.getUserEntity();
        user.setIsEnabled(true);
        userRepository.save(user);
        return "Account verified successfully";
    }

    @Override
    public String resendVerificationEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("No user associated with email");
        }

        Optional<ConfirmationToken> confirmationTokenOptional = confirmationTokenRepository.findByUserEntityEmail(email);
        if (confirmationTokenOptional.isEmpty()) {
            throw new ResourceNotFoundException("No token found");
        }

        ConfirmationToken confirmationToken = confirmationTokenOptional.get();
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ValidationException("Email already confirmed");
        }

        String token = UUID.randomUUID().toString();
        confirmationToken.setToken(token);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiredAt(LocalDateTime.now().plusMinutes(15));
        confirmationTokenService.createConfirmationToken(confirmationToken);

        String htmlContent = emailService.buildEmailConfirmationEmail(confirmationToken.getToken());
        emailService.sendEmail(email, "Email Confirmation", htmlContent);

        return email;
    }

    @Override
    public LoginResponseDto login(LoginDto loginDto) {
        try {
            UserEntity user = userRepository.findByUsernameOrEmail(loginDto.getUsername(), loginDto.getUsername()).orElseThrow();

            if (!user.isEnabled()) {
                throw new UnauthorizedException("Account not confirmed");
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );

            // update last login timestamp
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
            String token = jwtService.generateToken(user);
            return new LoginResponseDto(token, modelMapper.map(user, UserDto.class));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Invalid username or password");
        } catch (NoSuchElementException e) {
            throw new UnauthorizedException("User not found");
        }
    }

    @Override
    public UserDto getAuthenticatedUser() {
        UserEntity userEntity = securityUtils.getCurrentUser();
        int notifications = notificationService.getUnreadNotifications().size();
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        userDto.setNotifications(notifications);
        return userDto;
    }
}