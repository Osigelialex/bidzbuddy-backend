package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.PaymentDto;
import com.example.biddingsystem.dto.TransactionDto;
import com.example.biddingsystem.enums.PaymentStatus;
import com.example.biddingsystem.exceptions.ValidationException;
import com.example.biddingsystem.models.Product;
import com.example.biddingsystem.models.Transaction;
import com.example.biddingsystem.repositories.ProductRepository;
import com.example.biddingsystem.repositories.TransactionRepository;
import com.example.biddingsystem.services.NotificationService;
import com.example.biddingsystem.services.TransactionService;
import com.example.biddingsystem.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private NotificationService notificationService;
    private SecurityUtils securityUtils;
    private ProductRepository productRepository;
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public Long createTransaction(PaymentDto paymentDto) {
        Optional<Product> productOptional = productRepository.findById(paymentDto.getProductId());
        if (productOptional.isEmpty()) {
            throw new ValidationException("Product not found");
        }

        // verify amount
        Product product = productOptional.get();
        if (!Objects.equals(paymentDto.getAmount(), product.getCurrentBid())) {
            throw new ValidationException("Invalid amount");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(paymentDto.getAmount());
        transaction.setPaymentStatus(PaymentStatus.PENDING);
        transaction.setUserId(securityUtils.getCurrentUser().getId());
        transaction.setProductId(paymentDto.getProductId());

        transaction = transactionRepository.save(transaction);
        return transaction.getTransactionId();
    }

    @Override
    public List<TransactionDto> getTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }

        return transactions.stream().map(transaction -> modelMapper.map(transaction, TransactionDto.class)).toList();
    }

    @Override
    public void updateTransactionStatus(Long transactionId, String paymentStatus) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
        if (transactionOptional.isEmpty()) {
            throw new ValidationException("Transaction not found");
        }

        Transaction transaction = transactionOptional.get();
        transaction.setPaymentStatus(PaymentStatus.valueOf(paymentStatus));
        transactionRepository.save(transaction);
    }
}
