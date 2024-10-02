package com.collins.TransactionNotificationService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionNotificationDTO {
    private String version;
    private String messageId;
    private String nibssRail;
    private String myType;
    private String myAccount;
    private String myId;
    private String myInstitutionCode;
    private String status;
    private String currency;
    private String refTransactionId;
    private String otherName;
    private String otherAccount;
    private String otherInstitutionCode;
    private String otherId;
    private String transactionType;
    private String transactionTime;
    private double value;
}
