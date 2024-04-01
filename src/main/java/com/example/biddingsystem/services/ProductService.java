package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.ProductCreationDto;
import com.example.biddingsystem.dto.ProductDto;

import java.io.IOException;
import java.util.List;


public interface ProductService {
    List<ProductDto> getAllProducts(Long categoryId, String condition, Double minimumBid);
    ProductDto getProductById(Long id);
    ProductDto createProduct(ProductCreationDto product) throws IOException;
    void deleteProduct(Long id);
    List<ProductDto> searchProducts(String productName);
    List<ProductDto> getProductsByCategory(Long categoryId);
    List<ProductDto> getProductsByCondition(String condition);
    List<ProductDto> getProductsByMinimumBid(Double minimumBid);
    List<ProductDto> getProductsByCategoryAndCondition(Long categoryId, String condition);
    List<ProductDto> getProductsByCategoryAndMinimumBid(Long categoryId, Double minimumBid);
    List<ProductDto> getProductsByConditionAndMinimumBid(String condition, Double minimumBid);
    List<ProductDto> getProductsByCategoryAndConditionAndMinimumBid(Long categoryId, String condition, Double minimumBid);
}
