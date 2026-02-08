package com.nexusstream.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="videos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @Column(unique=true,nullable=false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable=false)
    private String description;

    @Column(unique=true,nullable=false)
    private String url;

    @Column(nullable=false)
    private String category;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private SubscriptionTier tier = SubscriptionTier.FREE;

    @Column(nullable=false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}
