package com.example.biddingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseDto {
    private Boolean status;
    private String message;
    private PaystackData data;
}
