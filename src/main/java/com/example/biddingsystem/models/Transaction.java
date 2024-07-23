package com.example.biddingsystem.models;

import com.example.biddingsystem.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Long userId;
    private Long productId;
    private Long amount;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;
    private Date timestamp = new Date();
}
