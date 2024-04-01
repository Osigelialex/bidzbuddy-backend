package com.example.biddingsystem.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataConflictException extends RuntimeException {
    public DataConflictException(String message) {
        super(message);
    }
}
