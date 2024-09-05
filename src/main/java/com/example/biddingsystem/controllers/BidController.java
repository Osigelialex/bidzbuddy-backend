package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.BidDto;
import com.example.biddingsystem.dto.BidListDto;
import com.example.biddingsystem.dto.UserBidsDto;
import com.example.biddingsystem.services.BiddingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bids")
@Tag(name = "Bidding", description = "Bidding functionalities")
public class BidController {

    @Autowired
    private BiddingService biddingService;

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<UserBidsDto>> getUserBids(
            @RequestParam(required = false) Boolean winningBids
    ) {
        return ResponseEntity.ok(biddingService.getUserBids(winningBids != null ? winningBids : false));
    }

    @GetMapping("/recent")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<UserBidsDto>> getLatestBids() {
        return ResponseEntity.ok(biddingService.getLatestBids());
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<BidListDto>> getAllBids() {
        return ResponseEntity.ok(biddingService.getAllBids());
    }

    @PostMapping("/place/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> placeBid(@PathVariable("productId") Long productId, @RequestBody BidDto bidDto) {
        biddingService.placeBid(productId, bidDto);
        return ResponseEntity.ok("Bid placed successfully");
    }

    @GetMapping("/list/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<BidListDto>> getBiddingList(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(biddingService.getBiddingList(productId));
    }

    @PatchMapping("/close/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> closeBid(@PathVariable("productId") Long productId) {
        biddingService.closeBidding(productId);
        return ResponseEntity.ok("Bidding closed successfully");
    }

    @PatchMapping("/winner/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> declareWinner(@PathVariable("productId") Long productId) {
        biddingService.declareWinner(productId);
        return ResponseEntity.ok("Winner declared successfully");
    }

    @PatchMapping("/reopen/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> reopenBidding(@PathVariable("productId") Long productId) {
        biddingService.reopenBidding(productId);
        return ResponseEntity.ok("Bidding reopened successfully");
    }

    @GetMapping("winning-bid/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<BidListDto> getWinningBidding(@PathVariable("productId") Long productId) {
        return new ResponseEntity<>(biddingService.getWinningBid(productId), HttpStatus.OK);
    }
}
