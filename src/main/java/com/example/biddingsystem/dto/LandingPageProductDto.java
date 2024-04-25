package com.example.biddingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandingPageProductDto {
    private Long id;
    private String name;
    private String description;
    private Long minimumBid;
    private Long remainingTime;
    private String productImageUrl;
}
