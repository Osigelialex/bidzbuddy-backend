package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.LandingPageProductDto;
import com.example.biddingsystem.dto.ProductCreationDto;
import com.example.biddingsystem.dto.ProductDto;
import com.example.biddingsystem.services.ProductService;
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
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
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

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductDto> createProduct(@ModelAttribute @Valid ProductCreationDto product) throws IOException {
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/unprotected")
    public ResponseEntity<List<LandingPageProductDto>> getLandingPageProducts() {
        return new ResponseEntity<>(productService.getLandingPageProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
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
