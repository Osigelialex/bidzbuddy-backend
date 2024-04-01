package com.example.biddingsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidDto {

    @NotNull(message = "Bid amount is required")
    private Long bidAmount;
}
