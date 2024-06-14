package com.example.biddingsystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreationDto {

    @NotNull(message = "Product name cannot be null")
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @NotNull(message = "Product description cannot be null")
    @NotBlank(message = "Product description cannot be blank")
    private String description;

    @NotNull(message = "Product category cannot be null")
    @Min(value = 1, message = "Product category cannot be null")
    private Long categoryId;

    @NotNull(message = "Product condition cannot be null")
    @NotBlank(message = "Product condition cannot be blank")
    private String condition;

    @NotNull(message = "Product duration cannot be null")
    @Min(value = 1, message = "Product duration cannot be less than 1")
    private int duration;

    @NotNull(message = "Product minimum bid cannot be null")
    @Min(value = 5000, message = "Product minimum bid cannot be less than 5000")
    private Long minimumBid;

    @NotNull(message = "Product image must be provided")
    private MultipartFile productImage;
}
