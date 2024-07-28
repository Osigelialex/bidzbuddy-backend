package com.example.biddingsystem.seeders;

import com.example.biddingsystem.enums.Condition;
import com.example.biddingsystem.enums.Role;
import com.example.biddingsystem.models.Category;
import com.example.biddingsystem.models.Product;
import com.example.biddingsystem.models.UserEntity;
import com.example.biddingsystem.repositories.CategoryRepository;
import com.example.biddingsystem.repositories.ProductRepository;
import com.example.biddingsystem.repositories.UserRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Component
public class ProductSeeder implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final static Logger logger = LoggerFactory.getLogger(ProductSeeder.class);
    Faker faker = new Faker();

    public ProductSeeder(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        logger.info("Seeding products...");

        if (productRepository.count() > 0) {
            logger.info("Products already exists in database. Skipping seeding...");
            return;
        }

        List<Category> categories = categoryRepository.findAll();

        // create seller
        UserEntity seller = new UserEntity();
        seller.setEmail("seller@gmail.com");
        seller.setUsername("seller");
        seller.setPassword("password");
        seller.setFirstname("John");
        seller.setLastname("Doe");
        seller.setRole(Role.SELLER);
        seller.setIsEnabled(true);
        userRepository.save(seller);

        for (int i = 0; i < 10; i++) {
            Long price = (long) (new Random().nextInt(10000) + 5000);

            // create a new product
            Product product = new Product();
            product.setMinimumBid(price);
            product.setCurrentBid(price);
            product.setName(faker.commerce().productName());
            product.setSeller(seller);
            product.setProductApproved(true);
            product.setDuration(14);
            product.setEndTime();
            product.setDescription(faker.lorem().sentence());
            product.setCondition(new Random().nextInt() % 2 == 0 ? Condition.NEW : Condition.USED);
            product.setProductImageUrl("https://res.cloudinary.com/dyktnfgye/image/upload/v1722173585/8_hyx9vy.png");
            product.setCategory(categories.get(i % categories.size()));
            productRepository.save(product);
        }

        logger.info("Products seeded successfully");
    }
}
