package com.example.biddingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebhookResponse {
    private String event;
    private Object data;
}
