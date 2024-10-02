package com.collins.TransactionNotificationService.controller;

import com.collins.TransactionNotificationService.service.NotificationService;
import com.collins.TransactionNotificationService.dtos.EncryptedNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tns")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    @PostMapping("/notify/me")
    public ResponseEntity<String> receiveNotification(@RequestBody EncryptedNotificationDTO encryptedNotification) {
        notificationService.processNotification(encryptedNotification);
        return ResponseEntity.ok("Notification received successfully");
    }
}
