package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.BidDto;
import com.example.biddingsystem.dto.BidListDto;
import com.example.biddingsystem.dto.UserBidsDto;
import com.example.biddingsystem.models.Bid;

import java.util.List;

public interface BiddingService {
    List<UserBidsDto> getUserBids();
    List<BidListDto> getAllBids();
    List<UserBidsDto> getLatestBids();
    void placeBid(Long productId, BidDto bidDto);
    List<BidListDto> getBiddingList(Long productId);
    BidListDto getWinningBid(Long productId);
    void closeBidding(Long productId);
    void reopenBidding(Long productId);
    void declareWinner(Long productId);
}
