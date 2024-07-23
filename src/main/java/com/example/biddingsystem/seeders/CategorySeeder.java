package com.example.biddingsystem.seeders;

import com.example.biddingsystem.models.Category;
import com.example.biddingsystem.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class CategorySeeder implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategorySeeder.class);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
         seedCategoryData();
    }

    public void seedCategoryData() {
        logger.info("Seeding category data...");

        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("Electronics"));
            categoryRepository.save(new Category("Fashion"));
            categoryRepository.save(new Category("Automotive"));
            categoryRepository.save(new Category("Real Estate"));
            categoryRepository.save(new Category("Furniture"));
            categoryRepository.save(new Category("Paintings and Digital Art"));
            categoryRepository.save(new Category("Crafts and DIY"));
            categoryRepository.save(new Category("Sports and Outdoors"));
            categoryRepository.save(new Category("Health and Beauty"));
            categoryRepository.save(new Category("Others"));

            logger.info("Category data seeded successfully.");
        } else {
            logger.info("Categories already exist in the database. Skipping seeding.");
        }
    }
}
