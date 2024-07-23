package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.AdminDashboardDto;
import com.example.biddingsystem.services.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@AllArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<AdminDashboardDto> adminDashboard() {
        return ResponseEntity.ok(dashboardService.adminDashboard());
    }
}
