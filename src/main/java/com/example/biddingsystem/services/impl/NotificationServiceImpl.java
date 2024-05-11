package com.example.biddingsystem.services.impl;

import com.example.biddingsystem.dto.NotificationDto;
import com.example.biddingsystem.exceptions.ResourceNotFoundException;
import com.example.biddingsystem.models.Notification;
import com.example.biddingsystem.models.UserEntity;
import com.example.biddingsystem.repositories.NotificationRepository;
import com.example.biddingsystem.repositories.UserRepository;
import com.example.biddingsystem.services.NotificationService;
import com.example.biddingsystem.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final SecurityUtils securityUtils;
    private final ModelMapper modelMapper;

    @Override
    public void sendNotification(String message, Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        UserEntity user = userOptional.get();

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(user);
        notificationRepository.save(notification);
    }

    @Override
    public void markAsRead(Long notificationId) {
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        if (notificationOptional.isEmpty()) {
            throw new ResourceNotFoundException("Notification not found");
        }

        Notification notification = notificationOptional.get();
        notification.setRead(true);
    }

    @Override
    public void markAllAsRead() {
        Optional<UserEntity> userOptional = userRepository.findById(securityUtils.getCurrentUser().getId());
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        UserEntity user = userOptional.get();
        List<Notification> userNotifications = notificationRepository.findByUser(userOptional.get());
        if (userNotifications.isEmpty()) {
            return;
        }
        userNotifications.forEach(notification -> notification.setRead(true));
        userRepository.save(user);
    }

    @Override
    public void deleteNotification(Long notificationId) {
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        if (notificationOptional.isEmpty()) {
            throw new ResourceNotFoundException("Notification not found");
        }

        Notification notification = notificationOptional.get();
        notificationRepository.delete(notification);
    }

    @Override
    public void deleteAllNotifications() {
        Optional<UserEntity> userOptional = userRepository.findById(securityUtils.getCurrentUser().getId());
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        UserEntity user = userOptional.get();
        List<Notification> userNotifications = notificationRepository.findByUser(user);
        userNotifications.forEach(notificationRepository::delete);
    }

    @Override
    public Integer getUnreadNotificationsCount() {
        Optional<UserEntity> userOptional = userRepository.findById(securityUtils.getCurrentUser().getId());
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        return notificationRepository.findByUserAndIsReadFalse(userOptional.get()).size();
    }

    @Override
    public List<NotificationDto> getNotifications() {
        Optional<UserEntity> userOptional = userRepository.findById(securityUtils.getCurrentUser().getId());
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        List<Notification> notifications = notificationRepository.findByUser(userOptional.get());
        return notifications.stream().map(notification -> modelMapper.map(notification, NotificationDto.class)).toList();
    }

    @Override
    public List<NotificationDto> getUnreadNotifications() {
        Optional<UserEntity> userOptional = userRepository.findById(securityUtils.getCurrentUser().getId());
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        List<Notification> notifications = notificationRepository.findByUserAndIsReadFalse(userOptional.get());
        return notifications.stream().map(notification -> modelMapper.map(notification, NotificationDto.class)).toList();
    }
}
