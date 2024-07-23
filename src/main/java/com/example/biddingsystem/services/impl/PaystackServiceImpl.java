package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.PaymentDto;
import com.example.biddingsystem.dto.PaymentResponseDto;
import com.example.biddingsystem.enums.PaymentStatus;
import com.example.biddingsystem.models.PaymentPayload;
import com.example.biddingsystem.exceptions.ValidationException;
import com.example.biddingsystem.repositories.ProductRepository;
import com.example.biddingsystem.services.PaystackService;
import com.example.biddingsystem.services.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cloudinary.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class PaystackServiceImpl implements PaystackService {
    private final TransactionService transactionService;

    public PaystackServiceImpl(TransactionService transactionService, ObjectMapper objectMapper) {
        this.transactionService = transactionService;
    }

    private final RestClient restClient = RestClient.create();
    private final static Logger logger = LoggerFactory.getLogger(PaystackServiceImpl.class);

    @Value("${paystack.secret}")
    private String paystackSecret;

    @Override
    public String initiatePayment(PaymentDto paymentDto) {
        Long transactionId = transactionService.createTransaction(paymentDto);
        String PAYSTACK_REFERENCE = "REF-" + transactionId;

        // make a request to the paystack api to initiate payment
        String PAYSTACK_INITIATE_PAYMENT_URL = "https://api.paystack.co/transaction/initialize";
        ResponseEntity<PaymentResponseDto> paystackResponse = restClient.post()
                .uri(PAYSTACK_INITIATE_PAYMENT_URL)
                .header("Authorization", "Bearer " + paystackSecret)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new PaymentPayload(paymentDto.getEmail(), paymentDto.getAmount() * 100, PAYSTACK_REFERENCE))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    logger.error("Error initializing payment: {}", response);
                    throw new ValidationException("Error initializing payment");
                }).toEntity(PaymentResponseDto.class);

        return Objects.requireNonNull(paystackResponse.getBody()).getData().getAuthorization_url();
    }

    @Override
    public void webHook(String payload, String signature) {
        if (!verifyWebhookSignature(payload, signature)) {
            logger.error("Invalid webhook signature");
            throw new ValidationException("Invalid webhook signature");
        }

        JSONObject paystackEvent = new JSONObject(payload);
        JSONObject data = paystackEvent.getJSONObject("data");
        handleEvent(paystackEvent.getString("event"), data.getString("reference"));
    }

    public boolean verifyWebhookSignature(String payload, String signature) {
        try {
            String HMAC_SHA512 = "HmacSHA512";
            byte [] byteKey = paystackSecret.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
            Mac sha512_HMAC = Mac.getInstance(HMAC_SHA512);
            sha512_HMAC.init(keySpec);
            byte [] mac_data = sha512_HMAC
                    .doFinal(payload.getBytes(StandardCharsets.UTF_8));
            String result = bytesToHex(mac_data);
            return result.toLowerCase().equals(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error("Error verifying webhook signature", e);
            return false;
        }
    }

    public void handleEvent(String event, String reference) {
        if (!reference.startsWith("REF-")) {
            logger.error("Invalid reference: {}", reference);
            throw new ValidationException("Invalid reference");
        }

        Long transactionId = Long.parseLong(reference.substring(reference.indexOf("-") + 1));

        switch (event) {
            case "charge.success", "transfer.success":
                transactionService.updateTransactionStatus(transactionId, String.valueOf(PaymentStatus.SUCCESSFUL));
                break;
            case "charge.failed", "transfer.failed":
                transactionService.updateTransactionStatus(transactionId, String.valueOf(PaymentStatus.FAILED));
                break;
            default:
                logger.info("Unknown event type: {}", event);
        }
    }

    public static String bytesToHex(byte [] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
