package com.example.biddingsystem.services;

import com.example.biddingsystem.dto.NotificationDto;
import com.example.biddingsystem.models.Notification;

import java.util.List;

public interface NotificationService {
    void sendNotification(String message, Long userId);
    void markAsRead(Long notificationId);
    void markAllAsRead();
    void deleteNotification(Long notificationId);
    void deleteAllNotifications();
    List<NotificationDto> getNotifications();
    List<NotificationDto> getUnreadNotifications();
}
