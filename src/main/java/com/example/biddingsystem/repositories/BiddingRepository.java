package com.example.biddingsystem.repositories;

import com.example.biddingsystem.dto.BidListDto;
import com.example.biddingsystem.models.Bid;
import com.example.biddingsystem.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiddingRepository extends JpaRepository<Bid, String> {
    List<Bid> findBidsByProductIdOrderByBidAmountDesc(Long productId);
    Bid findByProductIdAndIsWinningBidTrue(Long productId);
    List<Bid> findByProductIdAndIsWinningBidFalseAndBidderNot(Long productId, UserEntity winner);
    @Query("SELECT DISTINCT b.bidder FROM Bid b WHERE b.product.id = :productId")
    List<UserEntity> findDistinctBiddersByProductId(Long productId);
}
