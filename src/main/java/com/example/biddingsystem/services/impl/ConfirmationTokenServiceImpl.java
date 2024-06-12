package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.exceptions.ResourceNotFoundException;
import com.example.biddingsystem.models.ConfirmationToken;
import com.example.biddingsystem.repositories.ConfirmationTokenRepository;
import com.example.biddingsystem.services.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public ConfirmationToken getConfirmationToken(String token) {
        Optional<ConfirmationToken> confirmationTokenOptional = confirmationTokenRepository.findByToken(token);
        if (confirmationTokenOptional.isEmpty()) {
            throw new ResourceNotFoundException("Token not found");
        }

        return confirmationTokenOptional.get();
    }

    @Override
    public void createConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }
}
