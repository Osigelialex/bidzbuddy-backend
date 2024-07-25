package com.example.biddingsystem.seeders;

import com.example.biddingsystem.enums.Condition;
import com.example.biddingsystem.enums.Role;
import com.example.biddingsystem.models.Category;
import com.example.biddingsystem.models.Product;
import com.example.biddingsystem.models.UserEntity;
import com.example.biddingsystem.repositories.CategoryRepository;
import com.example.biddingsystem.repositories.ProductRepository;
import com.example.biddingsystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Component
@AllArgsConstructor
public class ProductSeeder implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final static Logger logger = LoggerFactory.getLogger(ProductSeeder.class);

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
        seller.setId(2L);
        seller.setEmail("seller@gmail.com");
        seller.setUsername("seller");
        seller.setPassword("password");
        seller.setFirstname("John");
        seller.setLastname("Doe");
        seller.setRole(Role.SELLER);
        seller.setIsEnabled(true);
        userRepository.save(seller);

        for (int i = 0; i < 10; i++) {
            // create a new product
            Product product = new Product();
            product.setDescription("Product description " + i);
            product.setMinimumBid(10000L);
            product.setCurrentBid(10000L);
            product.setName("18k Gold Necklace");
            product.setSeller(seller);
            product.setProductApproved(true);
            product.setDuration(14);
            product.setEndTime();
            product.setDescription("The necklace features a delicate chain made from the finest 18k gold," +
                    "offering a perfect blend of durability and luxurious shine. " +
                    "Each link is meticulously crafted to ensure a smooth and fluid drape around your neck, " +
                    "providing a comfortable and elegant fit.");
            product.setCondition(new Random().nextInt() % 2 == 0 ? Condition.NEW : Condition.USED);
            product.setProductImageUrl("http://res.cloudinary.com/dyktnfgye/image/upload/v1721558430/file_dgygdy.jpg");
            product.setCategory(categories.get(i % categories.size()));
            productRepository.save(product);
        }

        logger.info("Products seeded successfully");
    }
}
