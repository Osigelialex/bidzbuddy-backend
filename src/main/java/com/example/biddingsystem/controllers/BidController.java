package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.BidDto;
import com.example.biddingsystem.dto.BidListDto;
import com.example.biddingsystem.dto.UserBidsDto;
import com.example.biddingsystem.services.BiddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bids")
@CrossOrigin(origins = "http://localhost:5173")
public class BidController {

    @Autowired
    private BiddingService biddingService;

    @GetMapping
    public ResponseEntity<List<UserBidsDto>> getUserBids() {
        return ResponseEntity.ok(biddingService.getUserBids());
    }

    @GetMapping("/all")
    public ResponseEntity<List<BidListDto>> getAllBids() {
        return ResponseEntity.ok(biddingService.getAllBids());
    }

    @PostMapping("/place/{productId}")
    public ResponseEntity<String> placeBid(@PathVariable("productId") Long productId, @RequestBody BidDto bidDto) {
        biddingService.placeBid(productId, bidDto);
        return ResponseEntity.ok("Bid placed successfully");
    }

    @GetMapping("/list/{productId}")
    public ResponseEntity<List<BidListDto>> getBiddingList(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(biddingService.getBiddingList(productId));
    }

    @PatchMapping("/close/{productId}")
    public ResponseEntity<String> closeBid(@PathVariable("productId") Long productId) {
        biddingService.closeBidding(productId);
        return ResponseEntity.ok("Bidding closed successfully");
    }

    @PatchMapping("/winner/{productId}")
    public ResponseEntity<String> declareWinner(@PathVariable("productId") Long productId) {
        biddingService.declareWinner(productId);
        return ResponseEntity.ok("Winner declared successfully");
    }

    @PatchMapping("/reopen/{productId}")
    public ResponseEntity<String> reopenBidding(@PathVariable("productId") Long productId) {
        biddingService.reopenBidding(productId);
        return ResponseEntity.ok("Bidding reopened successfully");
    }

    @GetMapping("winning-bid/{productId}")
    public ResponseEntity<BidListDto> getWinningBidding(@PathVariable("productId") Long productId) {
        return new ResponseEntity<>(biddingService.getWinningBid(productId), HttpStatus.OK);
    }
}
