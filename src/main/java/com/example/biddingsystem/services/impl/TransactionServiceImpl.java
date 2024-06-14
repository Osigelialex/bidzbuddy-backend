package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.TransactionCreationDto;
import com.example.biddingsystem.dto.TransactionDto;
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
    public void createTransaction(TransactionCreationDto transactionCreationDto) {
        Optional<Product> productOptional = productRepository.findById(transactionCreationDto.getProductId());
        if (productOptional.isEmpty()) {
            throw new ValidationException("Product not found");
        }

        Product product = productOptional.get();
        product.setPaid(true);

        productRepository.save(product);

        Transaction transaction = new Transaction();
        transaction.setProductId(transactionCreationDto.getProductId());
        transaction.setAmount(transactionCreationDto.getAmount());
        transaction.setUserId(securityUtils.getCurrentUser().getId());
        transaction.setPaystackReference(transactionCreationDto.getPaystackReference());

        transactionRepository.save(transaction);

        // alert the user that the transaction was successful
        notificationService.sendNotification("Your payment has been confirmed", securityUtils.getCurrentUser().getId());
    }

    @Override
    public List<TransactionDto> getTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }

        return transactions.stream().map(transaction -> modelMapper.map(transaction, TransactionDto.class)).toList();
    }
}
