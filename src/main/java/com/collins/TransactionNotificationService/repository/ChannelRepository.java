package com.collins.TransactionNotificationService.repository;

import com.collins.TransactionNotificationService.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<ChannelEntity,Long> {
    ChannelEntity findByChannelCode(String nibssRail);

    boolean existsByChannelCode(String channelCode);
}
