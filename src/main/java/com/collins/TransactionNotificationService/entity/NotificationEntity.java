package com.collins.TransactionNotificationService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "tns_entries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "encrypted_message_json", columnDefinition = "LONGTEXT")
    private String encryptedMessageJson;

    @Column(name = "decrypted_message_json", columnDefinition = "LONGTEXT")
    private String decryptedMessageJson;

    @Basic(optional = false)
    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "error_response", columnDefinition = "LONGTEXT")
    private String errorResponse;


    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "is_published")
    @Basic(optional = false)
    private boolean isPublished;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


    public NotificationEntity(String statusCode){
        this.statusCode = statusCode;
        this.isPublished = false;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

}
