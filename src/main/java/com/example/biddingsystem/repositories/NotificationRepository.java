package com.example.biddingsystem.repositories;

import com.example.biddingsystem.models.Notification;
import com.example.biddingsystem.models.UserEntity;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndIsReadFalse(UserEntity user);
    List<Notification> findByUser(UserEntity user);
}
