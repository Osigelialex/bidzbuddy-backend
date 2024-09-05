package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.AdminUsersResponseDto;
import com.example.biddingsystem.services.AdminService;
import com.example.biddingsystem.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users")
public class UserController {
    private final UserService userService;
    private final AdminService adminService;

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    private ResponseEntity<List<AdminUsersResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/users/delete/{username}")
    @SecurityRequirement(name = "Bearer Authentication")
    private ResponseEntity<String> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PatchMapping("/users/lock/{username}")
    @SecurityRequirement(name = "Bearer Authentication")
    private ResponseEntity<String> lockUser(@PathVariable("username") String username) {
        adminService.lockUser(username);
        return ResponseEntity.ok("User locked successfully");
    }

    @PatchMapping("/users/unlock/{username}")
    @SecurityRequirement(name = "Bearer Authentication")
    private ResponseEntity<String> unlockUser(@PathVariable("username") String username) {
        adminService.unlockUser(username);
        return ResponseEntity.ok("User unlocked successfully");
    }
}
