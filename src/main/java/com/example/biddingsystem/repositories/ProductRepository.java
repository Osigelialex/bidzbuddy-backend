package com.example.biddingsystem.repositories;

import com.example.biddingsystem.enums.Condition;
import com.example.biddingsystem.models.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsById(Long productId);

    @Override
    @Query(value = "SELECT p FROM Product p WHERE p.productApproved = true ORDER BY p.id DESC")
    List<Product> findAll();

    @Query(value = "SELECT p FROM Product p WHERE p.productApproved = true ORDER BY p.id DESC")
    List<Product> findTop6ByProductApprovedIsTrueOrderByIdDesc(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.productApproved = false")
    List<Product> findProductsByProductApprovedIsFalse();

    @Query("SELECT p FROM Product p WHERE p.seller.id = :sellerId AND p.productApproved = true")
    List<Product> findProductsBySellerId(Long sellerId);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.productApproved = true")
    List<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.condition = :condition AND p.productApproved = true")
    List<Product> findProductsByCondition(@Param("condition") Condition condition);

    @Query("SELECT p FROM Product p WHERE p.minimumBid >= :minimumBid AND p.productApproved = true")
    List<Product> findProductsByMinimumBid(Double minimumBid);

    @Query("SELECT p FROM Product p WHERE p.productApproved = true AND p.name LIKE %:productName%")
    List<Product> findByNameIsContainingIgnoreCase(@Param("productName") String productName);

    @Query("SELECT p FROM Product p WHERE p.condition = :condition AND p.category.id = :categoryId AND p.productApproved = true")
    List<Product> findProductsByCategoryIdAndCondition(@Param("condition") Condition condition,
                                                       @Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.minimumBid >= :minimumBid AND p.productApproved = true")
    List<Product> findProductsByCategoryAndMinimumBid(@Param("categoryId") Long categoryId,
                                                      @Param("minimumBid") Double minimumBid);

    @Query("SELECT p FROM Product p WHERE p.condition = :condition AND p.minimumBid >= :minimumBid AND p.productApproved = true")
    List<Product> findProductsByConditionAndMinimumBid(@Param("condition") Condition condition,
                                                      @Param("minimumBid") Double minimumBid);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId " +
            "AND p.condition = :condition AND p.minimumBid >= :minimumBid AND p.productApproved = true")
    List<Product> findProductsByCategoryAndConditionAndMinimumBid(@Param("categoryId") Long categoryId,
                                                                  @Param("condition") Condition condition,
                                                                  @Param("minimumBid") Double minimumBid);
}
