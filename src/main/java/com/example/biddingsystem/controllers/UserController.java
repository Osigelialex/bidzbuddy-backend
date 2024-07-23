package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.AdminUsersResponseDto;
import com.example.biddingsystem.services.AdminService;
import com.example.biddingsystem.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final AdminService adminService;

    @GetMapping
    private ResponseEntity<List<AdminUsersResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/users/delete/{username}")
    private ResponseEntity<String> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PatchMapping("/users/lock/{username}")
    private ResponseEntity<String> lockUser(@PathVariable("username") String username) {
        adminService.lockUser(username);
        return ResponseEntity.ok("User locked successfully");
    }

    @PatchMapping("/users/unlock/{username}")
    private ResponseEntity<String> unlockUser(@PathVariable("username") String username) {
        adminService.unlockUser(username);
        return ResponseEntity.ok("User unlocked successfully");
    }
}
