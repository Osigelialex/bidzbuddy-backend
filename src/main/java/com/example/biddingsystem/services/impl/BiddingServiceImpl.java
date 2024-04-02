package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.BidDto;
import com.example.biddingsystem.dto.BidListDto;
import com.example.biddingsystem.exceptions.ResourceNotFoundException;
import com.example.biddingsystem.exceptions.ValidationException;
import com.example.biddingsystem.models.Bid;
import com.example.biddingsystem.models.Product;
import com.example.biddingsystem.models.UserEntity;
import com.example.biddingsystem.repositories.BiddingRepository;
import com.example.biddingsystem.repositories.ProductRepository;
import com.example.biddingsystem.services.BiddingService;
import com.example.biddingsystem.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class BiddingServiceImpl implements BiddingService {

    private final BiddingRepository biddingRepository;
    private final ProductRepository productRepository;
    private final SecurityUtils securityUtils;
    private final ModelMapper modelMapper;

    @Override
    public void placeBid(Long productId, BidDto bidDto) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        Product product = productOptional.get();
        if (product.isBiddingClosed()) {
            throw new ValidationException("Bidding is closed for this product");
        }

        if (bidDto.getBidAmount().equals(product.getMinimumBid()) &&
                !product.getMinimumBid().equals(product.getCurrentBid())) {
            throw new ValidationException("Bid amount must be at least " + product.getCurrentBid());
        }

        if (bidDto.getBidAmount() < product.getCurrentBid()) {
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
    public List<BidListDto> getBiddingList(Long productId) {
        List<Bid> biddingList = biddingRepository.findBidsByProductIdOrderByBidAmountDesc(productId);
        if (biddingList.isEmpty()) {
            return Collections.emptyList();
        }
        return biddingList.stream().map(bid -> modelMapper.map(bid, BidListDto.class)).collect(Collectors.toList());
    }

    @Override
    public BidListDto getWinningBid(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        Bid winningBid = biddingRepository.findWinningBid(productId);
        if (winningBid == null) {
            throw new ResourceNotFoundException("Product does not have a winning bid yet");
        }

        return modelMapper.map(winningBid, BidListDto.class);
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
    public void reopenBidding(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        Product product = productOptional.get();
        product.setBiddingClosed(false);
        productRepository.save(product);
    }

    @Override
    public void declareWinner(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        Product product = productOptional.get();
        if (!product.isBiddingClosed()) {
            throw new ValidationException("Bidding is not closed for this product");
        }

        List<Bid> biddingList = biddingRepository.findBidsByProductIdOrderByBidAmountDesc(productId);
        if (biddingList.isEmpty()) {
            throw new ResourceNotFoundException("No bids found for this product");
        }

        Bid winningBid = biddingList.get(0);
        UserEntity winner = winningBid.getBidder();

        winningBid.setIsWinningBid(true);
        product.setWinningBidder(winner);
        productRepository.save(product);
        biddingRepository.save(winningBid);
    }
}
