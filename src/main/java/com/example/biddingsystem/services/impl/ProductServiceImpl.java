package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.LandingPageProductDto;
import com.example.biddingsystem.dto.ProductCreationDto;
import com.example.biddingsystem.dto.ProductDto;
import com.example.biddingsystem.enums.Condition;
import com.example.biddingsystem.exceptions.BiddingUnauthorizedException;
import com.example.biddingsystem.exceptions.MediaUploadException;
import com.example.biddingsystem.exceptions.ResourceNotFoundException;
import com.example.biddingsystem.exceptions.ValidationException;
import com.example.biddingsystem.models.Category;
import com.example.biddingsystem.models.Notification;
import com.example.biddingsystem.models.Product;
import com.example.biddingsystem.models.UserEntity;
import com.example.biddingsystem.repositories.CategoryRepository;
import com.example.biddingsystem.repositories.ProductRepository;
import com.example.biddingsystem.repositories.UserRepository;
import com.example.biddingsystem.services.NotificationService;
import com.example.biddingsystem.services.ProductService;
import com.example.biddingsystem.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final FileUploadServiceImpl fileUploadService;
    private final CategoryRepository categoryRepository;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Override
    public List<ProductDto> getAllProducts(Long categoryId, String condition, Double minimumBid) {
        Condition conditionEnum = null;
        if (condition != null) {
            try {
                conditionEnum = Condition.valueOf(condition.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ValidationException("Condition must be NEW or USED");
            }
        }

        List<Product> products = null;
        if (categoryId != null && condition == null && minimumBid == null) {
            products = productRepository.findProductsByCategoryId(categoryId);
        } else if (categoryId == null && condition != null && minimumBid == null) {
            products = productRepository.findProductsByCondition(conditionEnum);
        } else if (categoryId != null && condition != null && minimumBid == null) {
            products = productRepository.findProductsByCategoryIdAndCondition(conditionEnum, categoryId);
        } else if (categoryId == null && condition == null && minimumBid != null) {
            products = productRepository.findProductsByMinimumBid(minimumBid);
        } else if (categoryId != null && condition == null) {
            products = productRepository.findProductsByCategoryAndMinimumBid(categoryId, minimumBid);
        } else {
            products = productRepository.findAll();
        }

        return products.stream().map(product -> {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDto.setRemainingTime(product.getRemainingTime());
            return productDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<LandingPageProductDto> getLandingPageProducts() {
        Pageable pageable = PageRequest.of(0, 6);
        Page<Product> page = productRepository.findAll(pageable);
        List<Product> products = page.getContent();
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        return products.stream().map(product -> {
            LandingPageProductDto landingPageProductDto = modelMapper.map(product, LandingPageProductDto.class);
            landingPageProductDto.setRemainingTime(product.getRemainingTime());
            return landingPageProductDto;
        }).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) throw new ValidationException("Product not found");
        ProductDto productDto = modelMapper.map(product.get(), ProductDto.class);
        productDto.setRemainingTime(productDto.getRemainingTime());
        return productDto;
    }

    @Override
    public ProductDto createProduct(ProductCreationDto product) throws IOException {

        Optional<Category> category = categoryRepository.findById(product.getCategoryId());

        if (category.isEmpty()) {
            throw new ValidationException("Category id must be provided");
        }

        try {
            Condition.valueOf(product.getCondition());
        } catch(IllegalArgumentException e) {
            throw new ValidationException("Condition must be either NEW or USED");
        }

        if (!Objects.equals(product.getCondition(), Condition.NEW.name())
                && !Objects.equals(product.getCondition(), Condition.USED.name())) {
            throw new ValidationException("Condition must be either NEW or USED");
        }

        String uploadedImageUrl;
        try {
            uploadedImageUrl = fileUploadService.uploadImage(product.getProductImage());
        } catch (IOException e) {
            throw new MediaUploadException("An error occurred while uploading image");
        }

        UserEntity currentUser = securityUtils.getCurrentUser();

        Product newProduct = new Product();
        newProduct.setSeller(currentUser);
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setCondition(Condition.valueOf(product.getCondition()));
        newProduct.setMinimumBid(product.getMinimumBid());
        newProduct.setCategory(category.get());
        newProduct.setProductImageUrl(uploadedImageUrl);

        // notify current user that product has been created
        notificationService.sendNotification(
                "Product with name " + newProduct.getName() + " created successfully",
                currentUser.getId()
        );

        ProductDto productDto = modelMapper.map(productRepository.save(newProduct), ProductDto.class);
        productDto.setRemainingTime(productDto.getRemainingTime());
        return productDto;
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        Product product = productOptional.get();
        productRepository.delete(product);

        // notify current user that product has been deleted
        notificationService.sendNotification(
                "Product with name " + product.getName() + " deleted successfully",
                product.getSeller().getId()
        );
    }

    @Override
    public List<ProductDto> searchProducts(String productName) {
        List<Product> products = productRepository.findByNameIsContainingIgnoreCase(productName);
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        return products.stream().map(product -> {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDto.setRemainingTime(productDto.getRemainingTime());
            return productDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findProductsByCategoryId(categoryId);
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        return products.stream().map(product -> {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDto.setRemainingTime(productDto.getRemainingTime());
            return productDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCondition(String condition) {
        Condition conditionEnum;
        try {
            conditionEnum = Condition.valueOf(condition.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Condition must be NEW or OLD");
        }
        List<Product> products = productRepository.findProductsByCondition(conditionEnum);
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        return products.stream().map(product -> {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDto.setRemainingTime(productDto.getRemainingTime());
            return productDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByMinimumBid(Double minimumBid) {
        if (minimumBid < 5000) {
            throw new ValidationException("Minimum bid must be greater than 5000");
        }
        List<Product> products = productRepository.findProductsByMinimumBid(minimumBid);
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        return products.stream().map(product -> {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDto.setRemainingTime(productDto.getRemainingTime());
            return productDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndCondition(Long categoryId, String condition) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new ResourceNotFoundException("Category not found");
        }

        Condition conditionEnum;
        try {
            conditionEnum = Condition.valueOf(condition.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Condition must be NEW or USED");
        }

        List<Product> products = productRepository.findProductsByCategoryIdAndCondition(conditionEnum, categoryId);
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        return products.stream().map(product -> {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDto.setRemainingTime(productDto.getRemainingTime());
            return productDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndMinimumBid(Long categoryId, Double minimumBid) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new ResourceNotFoundException("Category not found");
        }

        if (minimumBid < 5000) {
            throw new ValidationException("Minimum bid must be greater than 5000");
        }

        List<Product> products = productRepository.findProductsByCategoryAndMinimumBid(categoryId, minimumBid);
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        return products.stream().map(product -> {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDto.setRemainingTime(productDto.getRemainingTime());
            return productDto;
        })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByConditionAndMinimumBid(String condition, Double minimumBid) {
        Condition conditionEnum;
        try {
            conditionEnum = Condition.valueOf(condition.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Condition must be NEW or USED");
        }

        if (minimumBid < 5000) {
            throw new ValidationException("Minimum bid must be greater than 5000");
        }

        List<Product> products = productRepository.findProductsByConditionAndMinimumBid(conditionEnum, minimumBid);
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        return products.stream().map(product -> {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDto.setRemainingTime(productDto.getRemainingTime());
            return productDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndConditionAndMinimumBid(Long categoryId, String condition, Double minimumBid) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new ResourceNotFoundException("Category not found");
        }

        Condition conditionEnum;
        try {
            conditionEnum = Condition.valueOf(condition.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Condition must be NEW or USED");
        }

        if (minimumBid < 5000) {
            throw new ValidationException("Minimum bid must be greater than 5000");
        }

        List<Product> products = productRepository.findProductsByCategoryAndConditionAndMinimumBid(
                categoryId, conditionEnum, minimumBid);
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        return products.stream().map(product -> {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDto.setRemainingTime(productDto.getRemainingTime());
            return productDto;
        }).collect(Collectors.toList());
    }
}
