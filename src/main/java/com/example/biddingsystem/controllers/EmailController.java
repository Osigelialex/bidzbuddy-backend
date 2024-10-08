package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.SendMailDto;
import com.example.biddingsystem.services.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sendmail")
@Tag(name = "Emails")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/contact")
    public ResponseEntity<String> contactUs(@RequestBody SendMailDto sendMailDto) throws MessagingException {
        String htmlContent = emailService.buildContactUsEmail(sendMailDto);
        emailService.contactUsMail(sendMailDto.getEmail(), "Contact Us", htmlContent);
        return ResponseEntity.ok("Mail sent successfully");
    }

}
