package com.example.biddingsystem.controllers;

import com.example.biddingsystem.dto.NotificationDto;
import com.example.biddingsystem.dto.SendNotificationDto;
import com.example.biddingsystem.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody SendNotificationDto sendNotificationDto){
        notificationService.sendNotification(sendNotificationDto.getMessage(), sendNotificationDto.getUserId());
        return new ResponseEntity<>("Notification sent successfully", HttpStatus.OK);
    }

    @PatchMapping("/mark-as-read/{notificationId}")
    public ResponseEntity<String> markAsRead(@PathVariable("notificationId") Long notificationId){
        notificationService.markAsRead(notificationId);
        return new ResponseEntity<>("Notification marked as read", HttpStatus.OK);
    }

    @PatchMapping("/mark-all-as-read")
    public ResponseEntity<String> markAllAsRead(){
        notificationService.markAllAsRead();
        return new ResponseEntity<>("All notifications marked as read", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable("notificationId") Long notificationId){
        notificationService.deleteNotification(notificationId);
        return new ResponseEntity<>("Notification deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllNotifications(){
        notificationService.deleteAllNotifications();
        return new ResponseEntity<>("All notifications deleted successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getNotifications(){
        return new ResponseEntity<>(notificationService.getNotifications(), HttpStatus.OK);
    }

    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDto>> getUnreadNotifications(){
        return new ResponseEntity<>(notificationService.getUnreadNotifications(), HttpStatus.OK);
    }
}
