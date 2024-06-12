package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.SendMailDto;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
    void contactUsMail(String from, String subject, String content);
    String buildContactUsEmail(SendMailDto sendMailDto);
    String buildEmailConfirmationEmail(String confirmationToken);
}
