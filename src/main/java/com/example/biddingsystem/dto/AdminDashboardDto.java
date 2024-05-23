package com.example.biddingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardDto {
    private int totalUsers;
    private int totalProducts;
    private AtomicInteger totalBidAmount;
    private int totalCategories;
}
