package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.BidDto;
import com.example.biddingsystem.exceptions.ResourceNotFoundException;
import com.example.biddingsystem.exceptions.ValidationException;
import com.example.biddingsystem.models.Bid;
import com.example.biddingsystem.models.Product;
import com.example.biddingsystem.repositories.BiddingRepository;
import com.example.biddingsystem.repositories.ProductRepository;
import com.example.biddingsystem.services.BiddingService;
import com.example.biddingsystem.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class BiddingServiceImpl implements BiddingService {

    private final BiddingRepository biddingRepository;
    private final ProductRepository productRepository;
    private final SecurityUtils securityUtils;

    @Override
    public void placeBid(Long productId, BidDto bidDto) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        Product product = productOptional.get();
        if (bidDto.getBidAmount() < product.getMinimumBid()) {
            throw new ValidationException("Bid amount must be at least " + product.getMinimumBid());
        }

        if (bidDto.getBidAmount().equals(product.getMinimumBid()) && !product.getMinimumBid().equals(product.getCurrentBid())) {
            throw new ValidationException("Bid amount must be greater than " + product.getCurrentBid());
        }

        product.setCurrentBid(bidDto.getBidAmount());
        productRepository.save(product);

        Bid bid = new Bid();
        bid.setBidder(securityUtils.getCurrentUser());
        bid.setProduct(product);
        bid.setBidAmount(bidDto.getBidAmount());
        biddingRepository.save(bid);
    }

    @Override
    public void closeBidding(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        Product product = productOptional.get();
        product.setBiddingClosed(true);
        productRepository.save(product);
    }

    @Override
    public void declareWinner(Long productId) {

    }

    @Override
    public void cancelBidding(Long productId) {

    }

    @Override
    public void deleteBidsByProductId(Long productId) {

    }

    @Override
    public void deleteBidsByUserId(Long userId) {

    }

    @Override
    public void deleteBidsByBidderId(Long bidderId) {

    }

    @Override
    public void deleteBidsByBidAmount(Long bidAmount) {

    }

    @Override
    public void deleteBidsByIsWinningBid(Boolean isWinningBid) {

    }

    @Override
    public void deleteBidsByTimestamp(Long timestamp) {

    }

    @Override
    public void deleteAllBids() {

    }

    @Override
    public void deleteBidById(Long bidId) {

    }

    @Override
    public void deleteBidsByIds(List<Long> biddingIds) {

    }
}
