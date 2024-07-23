package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.PaymentDto;
import com.example.biddingsystem.dto.TransactionDto;
import com.example.biddingsystem.enums.PaymentStatus;

import java.util.List;

public interface TransactionService {
    Long createTransaction(PaymentDto paymentDto);
    List<TransactionDto> getTransactions();
    void updateTransactionStatus(Long transactionId, String paymentStatus);
}
