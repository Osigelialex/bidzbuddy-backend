package com.example.biddingsystem.services;

import com.example.biddingsystem.models.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getCategories();
    Category updateCategoryById(Long categoryId, Category category);
    void deleteCategoryById(Long categoryId);
}
