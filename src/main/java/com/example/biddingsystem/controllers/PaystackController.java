package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.PaymentDto;
import com.example.biddingsystem.services.PaystackService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1/paystack")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class PaystackController {
    private final PaystackService paystackService;

    @PostMapping
    public ResponseEntity<String> initiatePayment(@RequestBody PaymentDto paymentDto) {
        String authorizationUrl = paystackService.initiatePayment(paymentDto);
        return new ResponseEntity<>(authorizationUrl, HttpStatus.OK);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        String signature = request.getHeader("x-paystack-signature");
        paystackService.webHook(payload, signature);
        return new ResponseEntity<>("Webhook received", HttpStatus.OK);
    }
}
