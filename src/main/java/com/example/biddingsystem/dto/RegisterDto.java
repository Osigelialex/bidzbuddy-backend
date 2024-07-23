package com.example.biddingsystem.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    @NotNull(message = "firstname must be provided")
    @NotBlank(message = "firstname cannot be empty")
    private String firstname;

    @NotNull(message = "lastname must be provided")
    @NotBlank(message = "lastname cannot be empty")
    private String lastname;

    @NotNull(message = "username must be provided")
    @NotBlank(message = "username cannot be empty")
    private String username;

    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "password must be provided")
    @NotBlank(message = "password cannot be empty")
    @Size(min = 6, message = "password must be at least 6 characters")
    private String password;

    @NotNull(message = "role must be provided")
    private String role;
}
