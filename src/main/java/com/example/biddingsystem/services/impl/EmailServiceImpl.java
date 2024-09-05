package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.SendMailDto;
import com.example.biddingsystem.exceptions.ValidationException;
import com.example.biddingsystem.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Data
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String MAIL_USER;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    };

    @Override
    @Async
    public void sendEmail(String to, String subject, String content) {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
        try {
            helper.setTo(to);
            helper.setFrom(MAIL_USER);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ValidationException("Error sending email");
        }
    }

    @Override
    @Async
    public void contactUsMail(String from, String subject, String content) {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
        try {
            helper.setTo(MAIL_USER);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ValidationException("Error sending email");
        }
    }

    @Override
    public String buildContactUsEmail(SendMailDto sendMailDto) {
        return "<div style=\"font-family: Arial, sans-serif; margin: 20px;\">" +
                "<p style=\"color: #333; font-size: 16px;\">Dear BidzBuddy Team,</p>" +
                "<p style=\"color: #333; font-size: 16px; line-height: 1.5;\">We received an inquiry from <b>" +
                sendMailDto.getFirstName() +
                " " +
                sendMailDto.getLastName() +
                "</b> (&nbsp;" +
                sendMailDto.getEmail() +
                ") regarding:</p>" +
                "<p style=\"color: #333; font-size: 16px; line-height: 1.5;\">" +
                sendMailDto.getMessage() +
                "</p>" +
                "<p style=\"color: #333; font-size: 16px; line-height: 1.5;\">Sincerely,</p>" +
                "<p style=\"color: #333; font-size: 16px;\">BidzBuddy Team</p>" +
                "</div>";
    }

    @Override
    public String buildEmailConfirmationEmail(String confirmationToken) {
        return "<div style=\"font-family: Arial, sans-serif; margin: 20px;\">" +
                "<p style=\"color: #333; font-size: 16px;\">Welcome to BidzBuddy!" +
                ",</p>" +
                "<p style=\"color: #333; font-size: 16px; line-height: 1.5;\">Thank you for registering with BidzBuddy.</p>" +
                "<p style=\"color: #333; font-size: 16px; line-height: 1.5;\">Please confirm your email address by clicking this link: <a href=\"https://bidzbuddy.vercel.app/email-verification?token=" +
                confirmationToken +
                "\">Confirm Email</a></p>" +
                "<p style=\"color: #333; font-size: 16px; line-height: 1.5;\">This link will expire in 15 minutes.</p>" +
                "<p style=\"color: #333; font-size: 16px; line-height: 1.5;\">Sincerely,</p>" +
                "<p style=\"color: #333; font-size: 16px;\">BidzBuddy Team</p>" +
                "</div>";
    }
}
