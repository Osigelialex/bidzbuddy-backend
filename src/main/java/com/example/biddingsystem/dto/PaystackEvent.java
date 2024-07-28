package com.example.biddingsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaystackEvent {
    private String event;
    private String reference;
}
