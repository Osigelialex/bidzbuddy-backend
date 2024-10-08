package com.example.biddingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBidsDto {
    private String bidderUsername;
    private Long productId;
    private String productImageUrl;
    private String productName;
    private Boolean productBiddingClosed;
    private Long bidAmount;
    private Boolean isWinningBid;
    private Date timestamp;
}
