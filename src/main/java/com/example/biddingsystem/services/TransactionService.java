package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.TransactionCreationDto;
import com.example.biddingsystem.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    void createTransaction(TransactionCreationDto transactionCreationDto);
    List<TransactionDto> getTransactions();
}
