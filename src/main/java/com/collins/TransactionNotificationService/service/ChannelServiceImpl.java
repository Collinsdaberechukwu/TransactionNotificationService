package com.collins.TransactionNotificationService.service;

import com.collins.TransactionNotificationService.dtos.ChannelRequestDto;
import com.collins.TransactionNotificationService.dtos.ChannelUpdateDto;
import com.collins.TransactionNotificationService.entity.ChannelEntity;
import com.collins.TransactionNotificationService.repository.ChannelRepository;
import com.collins.TransactionNotificationService.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelServiceImpl implements ChannelService{

    private final ChannelRepository channelRepository;
    private final NotificationRepository notificationRepository;
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<ChannelEntity> createChannel(ChannelRequestDto channelRequestDto) {
        if(channelRepository.existsByChannelCode(channelRequestDto.getChannelCode())){
           throw new RuntimeException("Channel already exists");
        }
        ChannelEntity channelEntity = ChannelEntity.builder()
                .channelName(channelRequestDto.getChannelName())
                .channelCode(channelRequestDto.getChannelCode())
                .tnsCallBackUrls(channelRequestDto.getTnsCallBackUrls())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        channelEntity = channelRepository.save(channelEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(channelEntity);
    }

    @Override
    public ResponseEntity<ChannelEntity> updateChannel(ChannelUpdateDto channelUpdateDto) {
        Optional<ChannelEntity> existingChannelOptional = channelRepository.findById(channelUpdateDto.getId());


        if (existingChannelOptional.isPresent()) {
            ChannelEntity existingChannel = existingChannelOptional.get();
            log.info("Channel existence :{}", existingChannel.getChannelCode());
            if(!existingChannel.getChannelCode().equals(channelUpdateDto.getChannelCode())
            &&  channelRepository.existsByChannelCode(channelUpdateDto.getChannelCode())){
                throw new RuntimeException("Channel already exists, code must be unique");
            }
            existingChannel.setChannelCode(channelUpdateDto.getChannelCode());
            existingChannel.setChannelName(channelUpdateDto.getChannelName());
            existingChannel.setTnsCallBackUrls(channelUpdateDto.getTnsCallBackUrls());
            existingChannel.setUpdatedAt(new Date());
            ChannelEntity updatedChannel = channelRepository.save(existingChannel);
            return ResponseEntity.ok(updatedChannel);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ChannelEntity getChannelById(Long channelId) {
        return channelRepository.findById(channelId).orElse(null);
    }



    @Override
    public List<ChannelEntity> getAllChannels() {
        return channelRepository.findAll(); // This should work if channelRepository is set up correctly
    }


    @Override
    public ResponseEntity<String> deleteChannel(Long channelId) {
        if (channelRepository.existsById(channelId)) {
            channelRepository.deleteById(channelId);
            log.info("Channel deleted successfully");
            return ResponseEntity.ok("Deleted Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
