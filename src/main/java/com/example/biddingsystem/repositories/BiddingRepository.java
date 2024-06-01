package com.example.biddingsystem.repositories;

import com.example.biddingsystem.models.Bid;
import com.example.biddingsystem.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiddingRepository extends JpaRepository<Bid, String> {
    List<Bid> findBidsByBidderId(Long bidderId);
    @Query("SELECT b FROM Bid b ORDER BY b.timestamp DESC")
    Page<Bid> findBids(Pageable pageable);
    List<Bid> findBidsByProductIdOrderByBidAmountDesc(Long productId);
    Bid findByProductIdAndIsWinningBidTrue(Long productId);
    List<Bid> findByProductIdAndIsWinningBidFalseAndBidderNot(Long productId, UserEntity winner);
    @Query("SELECT DISTINCT b.bidder FROM Bid b WHERE b.product.id = :productId")
    List<UserEntity> findDistinctBiddersByProductId(Long productId);
}
