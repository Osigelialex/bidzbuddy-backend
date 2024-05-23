package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.AdminUsersResponseDto;
import com.example.biddingsystem.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;

    @GetMapping
    private ResponseEntity<List<AdminUsersResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/users/delete/{username}")
    private ResponseEntity<String> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }
}
