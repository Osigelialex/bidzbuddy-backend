package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.TransactionCreationDto;
import com.example.biddingsystem.dto.TransactionDto;
import com.example.biddingsystem.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getTransactions() {
        return ResponseEntity.ok(transactionService.getTransactions());
    }
}
