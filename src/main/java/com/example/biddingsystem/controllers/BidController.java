package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.BidDto;
import com.example.biddingsystem.services.BiddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bids")
public class BidController {

    @Autowired
    private BiddingService biddingService;

    @PostMapping("/place/{productId}")
    public ResponseEntity<String> placeBid(@PathVariable("productId") Long productId, @RequestBody BidDto bidDto) {
        biddingService.placeBid(productId, bidDto);
        return ResponseEntity.ok("Bid placed successfully");
    }
}
