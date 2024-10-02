package com.collins.TransactionNotificationService.service;

import com.collins.TransactionNotificationService.dtos.EncryptedNotificationDTO;
import com.collins.TransactionNotificationService.dtos.TransactionNotificationDTO;
import com.collins.TransactionNotificationService.entity.ChannelEntity;
import com.collins.TransactionNotificationService.entity.NotificationEntity;
import com.collins.TransactionNotificationService.repository.ChannelRepository;
import com.collins.TransactionNotificationService.repository.NotificationRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

import static com.collins.TransactionNotificationService.constants.ResponseCode.*;
import static com.collins.TransactionNotificationService.utils.DecryptUtils.decrypt;
import static com.collins.TransactionNotificationService.utils.NotificationUtils.getgson;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Value("${tns.key}")
    private String tnsKey;

    @Value("${tns.iv}")
    private String tnsIv;


    private final NotificationRepository notificationRepository;
    private final ChannelRepository channelRepository;

    private RestTemplate restTemplate;

    @Transactional
    @Override
    public void processNotification(EncryptedNotificationDTO encryptedNotification) {
        NotificationEntity notification = new NotificationEntity(PENDING_RESPONSE);
        notification.setEncryptedMessageJson(getgson().toJson(encryptedNotification));

        // Decrypt the message
        String decryptedMessage = decrypt(tnsKey, tnsIv, encryptedNotification.getMessage());

        if (StringUtils.isBlank(decryptedMessage)) {
            log.error("Unable to decrypt message for {}", encryptedNotification);
            notification.setErrorResponse("Error in decrypting message from TNS");
            notification.setStatusCode(DECRYPT_ERROR_RESPONSE);
        } else {
            try {
                // Deserialize the decrypted message
                TransactionNotificationDTO notificationDTO = getgson().fromJson(decryptedMessage, TransactionNotificationDTO.class);
                notification.setDecryptedMessageJson(getgson().toJson(notificationDTO));

                notification = notificationRepository.save(notification);

                List<ChannelEntity> channelEntities = channelRepository.findAll();

                if (!channelEntities.isEmpty()) {
                    for(ChannelEntity channel : channelEntities){
                        List<String> callbackUrls = channel.getTnsCallBackUrls();
                        for (String callbackUrl : callbackUrls) {
                            try {
                                ResponseEntity<String> response = restTemplate.postForEntity(callbackUrl, notification, String.class);
                                if (response.getStatusCode().is2xxSuccessful()) {
                                    log.info("Successfully notified {}: {}", callbackUrl, response.getBody());
                                    notification.setPublished(true);
                                    notification.setStatusCode(SUCCESS_RESPONSE);
                                } else {
                                    log.error("Failed to notify {}: Status Code: {}, Response: {}", callbackUrl, response.getStatusCode(), response.getBody());
                                    notification.setErrorResponse("Failed to notify callback URL");
                                    notification.setStatusCode(PENDING_RESPONSE);
                                }
                            } catch (Exception e) {
                                log.error("Exception occurred while notifying {}: {}", callbackUrl, e.getMessage(), e);
                                notification.setErrorResponse("Exception occurred while notifying: " + e.getMessage());
                                notification.setStatusCode(PENDING_RESPONSE);
                            }
                        }
                        notification.setUpdatedAt(new Date());
                    }

                } else {
                    log.warn("No channel found for NibssRail: {}", notificationDTO.getNibssRail());
                    notification.setErrorResponse("No channel found for NibssRail type");
                    notification.setStatusCode(FAILURE_RESPONSE);
                    notification.setUpdatedAt(new Date());
                }

            } catch (Exception e) {
                log.error("Error processing notification: {}", e.getMessage(), e);
                notification.setErrorResponse("Error occurred in processing TNS: " + e.getMessage());
                notification.setStatusCode(PENDING_RESPONSE);
                notification.setUpdatedAt(new Date());
            }
        }
        notificationRepository.save(notification);
    }
}
