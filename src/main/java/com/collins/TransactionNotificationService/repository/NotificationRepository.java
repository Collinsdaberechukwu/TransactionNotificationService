package com.collins.TransactionNotificationService.repository;

import com.collins.TransactionNotificationService.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {
}
