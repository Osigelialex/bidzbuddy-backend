package com.example.biddingsystem.repositories;

import com.example.biddingsystem.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @NonNull
    @Override
    @Query("SELECT r FROM Review r ORDER BY r.reviewedAt DESC")
    List<Review> findAll();
}
