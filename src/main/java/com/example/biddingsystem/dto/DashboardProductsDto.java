package com.example.biddingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardProductsDto {
    private Long id;
    private String name;
    private String categoryName;
    private String condition;
    private Long minimumBid;
    private Long currentBid;
    private boolean isBiddingClosed;
    private String productImageUrl;
}
