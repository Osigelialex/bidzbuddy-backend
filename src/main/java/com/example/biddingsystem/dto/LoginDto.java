package com.example.biddingsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotNull(message = "username is required")
    @NotBlank(message = "username cannot be blank")
    private String username;

    @NotNull(message = "password is required")
    @NotBlank(message = "password cannot be blank")
    private String password;
}
