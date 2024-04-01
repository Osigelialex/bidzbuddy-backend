package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.BidDto;

import java.util.List;

public interface BiddingService {
    void placeBid(Long productId, BidDto bidDto);
    void closeBidding(Long productId);
    void declareWinner(Long productId);
    void cancelBidding(Long productId);
    void deleteBidsByProductId(Long productId);
    void deleteBidsByUserId(Long userId);
    void deleteBidsByBidderId(Long bidderId);
    void deleteBidsByBidAmount(Long bidAmount);
    void deleteBidsByIsWinningBid(Boolean isWinningBid);
    void deleteBidsByTimestamp(Long timestamp);
    void deleteAllBids();
    void deleteBidById(Long bidId);
    void deleteBidsByIds(List<Long> biddingIds);
}
