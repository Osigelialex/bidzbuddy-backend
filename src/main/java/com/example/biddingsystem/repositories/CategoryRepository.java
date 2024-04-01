package com.example.biddingsystem.repositories;

import com.example.biddingsystem.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String categoryName);
    boolean existsByName(String categoryName);
}
