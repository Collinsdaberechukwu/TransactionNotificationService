package com.collins.TransactionNotificationService.service;

import com.collins.TransactionNotificationService.dtos.ChannelRequestDto;
import com.collins.TransactionNotificationService.dtos.ChannelUpdateDto;
import com.collins.TransactionNotificationService.entity.ChannelEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ChannelService {

    ResponseEntity<ChannelEntity> createChannel(ChannelRequestDto channelRequestDto);

    ResponseEntity<ChannelEntity> updateChannel(ChannelUpdateDto channelUpdateDto);

    ChannelEntity getChannelById(Long channelId);

    List<ChannelEntity> getAllChannels();

    ResponseEntity<String> deleteChannel(Long channelId);



}
