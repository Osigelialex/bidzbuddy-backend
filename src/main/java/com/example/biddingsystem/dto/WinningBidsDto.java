package com.example.biddingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WinningBidsDto {
    private Long productId;
    private String productName;
    private Boolean isWinningBid;
    private String bidderUsername;
    private Long bidAmount;
    private Date timestamp;
}
