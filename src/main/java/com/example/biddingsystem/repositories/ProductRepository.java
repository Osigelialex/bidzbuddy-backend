package com.example.biddingsystem.repositories;

import com.example.biddingsystem.enums.Condition;
import com.example.biddingsystem.models.Category;
import com.example.biddingsystem.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsBySellerId(Long sellerId);

    List<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId);

    List<Product> findProductsByCondition(@Param("condition") Condition condition);

    @Query("SELECT p FROM Product p WHERE p.minimumBid >= :minimumBid")
    List<Product> findProductsByMinimumBid(Double minimumBid);

    List<Product> findByNameIsContainingIgnoreCase(@Param("productName") String productName);

    @Query("SELECT p FROM Product p WHERE p.condition = :condition AND p.category.id = :categoryId")
    List<Product> findProductsByCategoryIdAndCondition(@Param("condition") Condition condition,
                                                       @Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.minimumBid >= :minimumBid")
    List<Product> findProductsByCategoryAndMinimumBid(@Param("categoryId") Long categoryId,
                                                      @Param("minimumBid") Double minimumBid);

    @Query("SELECT p FROM Product p WHERE p.condition = :condition AND p.minimumBid >= :minimumBid")
    List<Product> findProductsByConditionAndMinimumBid(@Param("condition") Condition condition,
                                                      @Param("minimumBid") Double minimumBid);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId " +
            "AND p.condition = :condition AND p.minimumBid >= :minimumBid")
    List<Product> findProductsByCategoryAndConditionAndMinimumBid(@Param("categoryId") Long categoryId,
                                                                  @Param("condition") Condition condition,
                                                                  @Param("minimumBid") Double minimumBid);
}
