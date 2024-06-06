package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.TransactionCreationDto;

public interface TransactionService {
    void createTransaction(TransactionCreationDto transactionCreationDto);
}
