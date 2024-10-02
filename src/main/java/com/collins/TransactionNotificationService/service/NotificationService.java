package com.collins.TransactionNotificationService.service;

import com.collins.TransactionNotificationService.dtos.EncryptedNotificationDTO;
import jakarta.transaction.Transactional;

public interface NotificationService {
    @Transactional
    void processNotification(EncryptedNotificationDTO encryptedNotification);
}
