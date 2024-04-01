package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.models.Category;
import com.example.biddingsystem.exceptions.DataConflictException;
import com.example.biddingsystem.exceptions.ValidationException;
import com.example.biddingsystem.repositories.CategoryRepository;
import com.example.biddingsystem.services.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Override
    public Category createCategory(Category category) {
        if (category.getName() == null || category.getDescription() == null) {
            throw new ValidationException("All fields are required");
        }

        if (categoryRepository.existsByName(category.getName())) {
            throw new DataConflictException("Category already exists");
        }

        categoryRepository.save(category);
        return category;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return Collections.emptyList();
        }
        return categories;
    }

    @Override
    public Category updateCategoryById(Long categoryId, Category category) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new ValidationException("Category not found");
        }

        if (category.getName() != null && category.getName().isEmpty()) {
            throw new ValidationException("Category name cannot be empty");
        }
        if (category.getDescription() != null && category.getDescription().isEmpty()) {
            throw new ValidationException("Category description cannot be empty");
        }

        if (category.getName() != null && categoryRepository.existsByName(category.getName())) {
            throw new ValidationException("Category already exists");
        }

        Category updatedcategory = categoryOptional.get();

        if (category.getName() != null) updatedcategory.setName(category.getName());
        if (category.getDescription() != null) updatedcategory.setDescription(category.getDescription());
        return categoryRepository.save(updatedcategory);
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new ValidationException("Category not found");
        }
        categoryRepository.delete(categoryOptional.get());
    }
}
