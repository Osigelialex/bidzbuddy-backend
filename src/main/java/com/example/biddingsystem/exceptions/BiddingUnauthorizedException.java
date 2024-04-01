package com.example.biddingsystem.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BiddingUnauthorizedException extends RuntimeException {
    public BiddingUnauthorizedException(String message) {
        super(message);
    }
}
