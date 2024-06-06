package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.TransactionCreationDto;
import com.example.biddingsystem.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<String> createTransaction(@RequestBody TransactionCreationDto transactionCreationDto) {
        transactionService.createTransaction(transactionCreationDto);
        return ResponseEntity.ok("Transaction created successfully");
    }
}
