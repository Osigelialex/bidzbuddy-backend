package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.CreateReviewDto;
import com.example.biddingsystem.dto.ReviewResponseDto;
import com.example.biddingsystem.services.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reviews")
@AllArgsConstructor
@Tag(name = "Reviews")
public class ReviewController {

    private ReviewService reviewService;

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> createReview(@RequestBody CreateReviewDto reviewDto) {
        reviewService.addReview(reviewDto);
        return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<ReviewResponseDto>> getReviews() {
        return new ResponseEntity<>(reviewService.getReviews(), HttpStatus.OK);
    }
}
