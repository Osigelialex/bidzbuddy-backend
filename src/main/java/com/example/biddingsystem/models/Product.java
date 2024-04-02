package com.example.biddingsystem.models;

import com.example.biddingsystem.enums.Condition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Enumerated(value = EnumType.STRING)
    private Condition condition;
    private Long minimumBid;
    private Long currentBid;
    private boolean isBiddingClosed = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winning_bidder_id")
    private UserEntity winningBidder;

    @Column(length = 1024)
    private String productImageUrl;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Bid> bidList;
}
