package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.DashboardProductsDto;
import com.example.biddingsystem.dto.LandingPageProductDto;
import com.example.biddingsystem.dto.ProductCreationDto;
import com.example.biddingsystem.dto.ProductDto;

import java.io.IOException;
import java.util.List;


public interface ProductService {
    List<ProductDto> getProductsBySeller();
    List<DashboardProductsDto> getProductsForDashboard();
    void approveProduct(Long productId);
    void rejectProduct(Long productId);
    List<DashboardProductsDto> getUnapprovedProducts();
    void closeAuctionForProduct(Long productId);
    void reopenAuctionForProduct(Long productId);
    List<ProductDto> getAllProducts(Long categoryId, String condition, Double minimumBid);
    List<LandingPageProductDto> getLandingPageProducts();
    ProductDto getProductById(Long id);
    ProductDto createProduct(ProductCreationDto product) throws IOException;
    void deleteProduct(Long id);
    List<ProductDto> searchProducts(String productName);
}
