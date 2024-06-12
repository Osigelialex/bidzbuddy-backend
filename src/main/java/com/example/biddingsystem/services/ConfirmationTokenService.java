package com.example.biddingsystem.services;

import com.example.biddingsystem.models.ConfirmationToken;

public interface ConfirmationTokenService {
    ConfirmationToken getConfirmationToken(String token);
    void createConfirmationToken(ConfirmationToken confirmationToken);
}
