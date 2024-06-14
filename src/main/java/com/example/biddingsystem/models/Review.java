package com.example.biddingsystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1024)
    private String content;
    private int stars;

    @ManyToOne
    @JoinColumn(nullable = false, name = "reviewer")
    private UserEntity user;
    private Date reviewedAt = new Date();
}
