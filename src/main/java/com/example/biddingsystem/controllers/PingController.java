package com.example.biddingsystem.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
@Tag(name = "Ping")
public class PingController {

    @GetMapping
    public ResponseEntity<String> pingServer() {
        return ResponseEntity.ok("PONG");
    }
}
