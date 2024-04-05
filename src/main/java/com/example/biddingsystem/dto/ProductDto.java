package com.example.biddingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private String condition;
    private Long minimumBid;
    private Long currentBid;
    private String productImageUrl;
}
