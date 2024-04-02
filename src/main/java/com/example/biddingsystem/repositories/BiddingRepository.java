package com.example.biddingsystem.repositories;

import com.example.biddingsystem.dto.BidListDto;
import com.example.biddingsystem.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiddingRepository extends JpaRepository<Bid, String> {
    List<Bid> findBidsByProductIdOrderByBidAmountDesc(Long productId);

    @Query("SELECT b FROM Bid b WHERE b.isWinningBid = true AND b.product.id = :productId")
    Bid findWinningBid(Long productId);
}
