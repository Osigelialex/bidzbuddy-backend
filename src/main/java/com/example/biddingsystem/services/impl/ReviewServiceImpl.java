package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.CreateReviewDto;
import com.example.biddingsystem.dto.ReviewResponseDto;
import com.example.biddingsystem.models.Review;
import com.example.biddingsystem.models.UserEntity;
import com.example.biddingsystem.repositories.ReviewRepository;
import com.example.biddingsystem.services.ReviewService;
import com.example.biddingsystem.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final SecurityUtils securityUtils;
    private final ModelMapper modelMapper;

    @Override
    public void addReview(CreateReviewDto reviewDto) {
        UserEntity current_user = securityUtils.getCurrentUser();
        Review review = new Review();
        review.setUser(current_user);
        review.setStars(reviewDto.getStars());
        review.setContent(reviewDto.getContent());
        reviewRepository.save(review);
    }

    @Override
    public List<ReviewResponseDto> getReviews() {
        List<Review> reviews = reviewRepository.findAll();
        if (reviews.isEmpty()) {
            return Collections.emptyList();
        }
        return reviews.stream().map(review -> modelMapper.map(review, ReviewResponseDto.class)).toList();
    }
}
