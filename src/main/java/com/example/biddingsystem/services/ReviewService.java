package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.CreateReviewDto;
import com.example.biddingsystem.dto.ReviewResponseDto;

import java.util.List;

public interface ReviewService {
    void addReview(CreateReviewDto reviewDto);
    List<ReviewResponseDto> getReviews();
}
