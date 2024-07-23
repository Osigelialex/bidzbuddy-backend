package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.PaymentDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface PaystackService {
    String initiatePayment(PaymentDto paymentDto);
    void webHook(String payload, String signature) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException;
}
