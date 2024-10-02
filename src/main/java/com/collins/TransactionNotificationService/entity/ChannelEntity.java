package com.collins.TransactionNotificationService.entity;

import com.collins.TransactionNotificationService.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "channel")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "channel_name", nullable = false)
    private String channelName;

    @Column(name = "channel_code",unique = true, nullable = false)
    private String channelCode;

    // Store the callback URLs in a separate table
    @Convert(converter = StringListConverter.class)
    @Column(name = "callback_url")
    private List<String> tnsCallBackUrls;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
