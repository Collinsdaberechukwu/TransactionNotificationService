package com.collins.TransactionNotificationService.utils;

import com.collins.TransactionNotificationService.dtos.TransactionNotificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.collins.TransactionNotificationService.utils.NotificationUtils.getgson;

@Slf4j
public class DecryptUtils {
    private static final int GCM_TAG = 128;
    private static final String SYMMETRIC_ALGORITHM = "AES";
    private static final String SYMMETRIC_TRANSFORM = "AES/GCM/NoPadding";

    public static String decrypt(String key, String iv, String encryptedData) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(key), SYMMETRIC_ALGORITHM);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG, Base64.getDecoder().decode(iv));
            Cipher cipher = Cipher.getInstance(SYMMETRIC_TRANSFORM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            log.error("Unable to decrypt data", ex);
            return Strings.EMPTY;
        }
    }

    public static String encryptSymmetric(String data, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(SYMMETRIC_TRANSFORM);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG, Base64.getDecoder().decode(iv));
            SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(key), SYMMETRIC_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception ex) {
            log.error("Could not encrypt message: {}", ex.getMessage());
            ex.printStackTrace();
            return Strings.EMPTY;
        }
    }

    public static void main(String[] args) {

        TransactionNotificationDTO notificationDTO = TransactionNotificationDTO.builder()
                .version("1.0")
                .messageId("12345-abcde-67890-fghij")
                .nibssRail("NIP")
                .myType("individual")
                .myAccount("1234567890")
                .myId("abc-123")
                .myInstitutionCode("999001")
                .status("SUCCESS")
                .currency("NGN")
                .refTransactionId("txn-987654321")
                .otherName("Collins Okafor")
                .otherAccount("0987654321")
                .otherInstitutionCode("999002")
                .otherId("xyz-789")
                .transactionType("fund_transfer")
                .transactionTime("2024-09-27 12:30:00")
                .value(50000.00)
                .build();

      String encryptedData = encryptSymmetric(getgson().toJson(notificationDTO), "TfEURJdJSjndjJqTaseINOPhgfTFcYU4", "NfrYwTuyIfEA/lmwX6j7PHEHcpXA4Ylzq2BjPWHAEWU=");
        System.out.println(encryptedData);
        System.out.println(decrypt("TfEURJdJSjndjJqTaseINOPhgfTFcYU4", "NfrYwTuyIfEA/lmwX6j7PHEHcpXA4Ylzq2BjPWHAEWU=",
                encryptedData));
    }
}
