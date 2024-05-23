package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.AdminDashboardDto;
import com.example.biddingsystem.models.Bid;
import com.example.biddingsystem.repositories.BiddingRepository;
import com.example.biddingsystem.repositories.CategoryRepository;
import com.example.biddingsystem.repositories.ProductRepository;
import com.example.biddingsystem.repositories.UserRepository;
import com.example.biddingsystem.services.DashboardService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BiddingRepository bidRepository;
    private final ModelMapper modelMapper;

    @Override
    public AdminDashboardDto adminDashboard() {
        int totalUsers = userRepository.findAll().size();
        int totalProducts = productRepository.findAll().size();

        // Calculate total bidding amount
        AtomicInteger totalBidAmount = new AtomicInteger();
        List<Bid> bidList = bidRepository.findAll();
        bidList.forEach(bid -> totalBidAmount.addAndGet(Math.toIntExact(bid.getBidAmount())));
        int totalCategories = categoryRepository.findAll().size();

        return new AdminDashboardDto(totalUsers, totalProducts, totalBidAmount, totalCategories);
    }
}
