package com.example.biddingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaystackData {
    private String authorization_url;
    private String accessCode;
}
