package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.NotificationDto;
import com.example.biddingsystem.dto.SendNotificationDto;
import com.example.biddingsystem.services.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@AllArgsConstructor
@Tag(name = "Notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/count")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Integer> getNotificationCount(){
        return new ResponseEntity<>(notificationService.getUnreadNotificationsCount(), HttpStatus.OK);
    }

    @PostMapping("/send")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> sendNotification(@RequestBody SendNotificationDto sendNotificationDto){
        notificationService.sendNotification(sendNotificationDto.getMessage(), sendNotificationDto.getUserId());
        return new ResponseEntity<>("Notification sent successfully", HttpStatus.OK);
    }

    @PatchMapping("/mark-as-read/{notificationId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> markAsRead(@PathVariable("notificationId") Long notificationId){
        notificationService.markAsRead(notificationId);
        return new ResponseEntity<>("Notification marked as read", HttpStatus.OK);
    }

    @PatchMapping("/mark-all-as-read")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> markAllAsRead(){
        notificationService.markAllAsRead();
        return new ResponseEntity<>("All notifications marked as read", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{notificationId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> deleteNotification(@PathVariable("notificationId") Long notificationId){
        notificationService.deleteNotification(notificationId);
        return new ResponseEntity<>("Notification deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete-all")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> deleteAllNotifications(){
        notificationService.deleteAllNotifications();
        return new ResponseEntity<>("All notifications deleted successfully", HttpStatus.OK);
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<NotificationDto>> getNotifications(){
        return new ResponseEntity<>(notificationService.getNotifications(), HttpStatus.OK);
    }

    @GetMapping("/unread")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<NotificationDto>> getUnreadNotifications(){
        return new ResponseEntity<>(notificationService.getUnreadNotifications(), HttpStatus.OK);
    }
}
