package com.collins.TransactionNotificationService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelRequestDto {
    private String channelName;
    private String channelCode;
    private List<String> tnsCallBackUrls;
}
