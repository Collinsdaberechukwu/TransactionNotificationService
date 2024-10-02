package com.collins.TransactionNotificationService.controller;

import com.collins.TransactionNotificationService.dtos.ChannelRequestDto;
import com.collins.TransactionNotificationService.dtos.ChannelUpdateDto;
import com.collins.TransactionNotificationService.entity.ChannelEntity;
import com.collins.TransactionNotificationService.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping("/create")
    public ResponseEntity<ChannelEntity> createChannel(@RequestBody ChannelRequestDto channelRequestDto) {
        return channelService.createChannel(channelRequestDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChannelEntity> updateChannel(
            @PathVariable Long id,
            @RequestBody ChannelUpdateDto channelUpdateDto) {

        channelUpdateDto.setId(id);
        return channelService.updateChannel(channelUpdateDto);
    }


    @GetMapping("/achieve/{id}")
    public ResponseEntity<ChannelEntity> getChannelById(@PathVariable Long id) {
        ChannelEntity channel = channelService.getChannelById(id);
        if (channel != null) {
            return ResponseEntity.ok(channel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/get_all")
    public ResponseEntity<List<ChannelEntity>> getAllChannels() {
        List<ChannelEntity> channels = channelService.getAllChannels();
        return ResponseEntity.ok(channels);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteChannel(@PathVariable Long id) {
        return channelService.deleteChannel(id);
    }
}
