package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.DashboardProductsDto;
import com.example.biddingsystem.dto.LandingPageProductDto;
import com.example.biddingsystem.dto.ProductCreationDto;
import com.example.biddingsystem.dto.ProductDto;
import com.example.biddingsystem.services.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
@Tag(name = "Products")
public class ProductController {
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String condition,
            @RequestParam(required = false) Double minimumBid
    ) {
        List<ProductDto> products = productService.getAllProducts(categoryId, condition, minimumBid);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PatchMapping("/approve/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> approveProduct(@PathVariable("productId") Long productId) {
        productService.approveProduct(productId);
        return new ResponseEntity<>("Product approved successfully", HttpStatus.OK);
    }

    @PatchMapping("/reject/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> rejectProduct(@PathVariable("productId") Long productId) {
        productService.rejectProduct(productId);
        return new ResponseEntity<>("Product rejected successfully", HttpStatus.OK);
    }

    @GetMapping("/unapproved")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<DashboardProductsDto>> getUnapprovedProducts() {
        return new ResponseEntity<>(productService.getUnapprovedProducts(), HttpStatus.OK);
    }

    @GetMapping("/user")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<ProductDto>> getProductsBySeller() {
        return new ResponseEntity<>(productService.getProductsBySeller(), HttpStatus.OK);
    }

    @GetMapping("/dashboard")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<DashboardProductsDto>> getProductsForDashboard() {
        return new ResponseEntity<>(productService.getProductsForDashboard(), HttpStatus.OK);
    }

    @PatchMapping("/close/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> closeAuctionForProduct(@PathVariable("productId") Long productId) {
        productService.closeAuctionForProduct(productId);
        return new ResponseEntity<>("Auction closed successfully", HttpStatus.OK);
    }

    @PatchMapping("/reopen/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> reopenAuctionForProduct(@PathVariable("productId") Long productId) {
        productService.reopenAuctionForProduct(productId);
        return new ResponseEntity<>("Auction reopened successfully", HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ProductDto> createProduct(@ModelAttribute @Valid ProductCreationDto product) throws IOException {
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/unprotected")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<LandingPageProductDto>> getLandingPageProducts() {
        return new ResponseEntity<>(productService.getLandingPageProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam String productName) {
        List<ProductDto> products = productService.searchProducts(productName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
