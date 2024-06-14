package com.example.biddingsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDto {

    @NotNull(message = "content cannot be empty")
    private String content;

    @NotNull(message = "stars must be provided")
    private int stars;
}
