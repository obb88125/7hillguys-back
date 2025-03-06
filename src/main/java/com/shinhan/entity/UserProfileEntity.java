package com.shinhan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@ToString
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "user_profiles")
public class UserProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userProfileId;

    @Column(nullable = false)
    private Integer userId;

    @Column(columnDefinition = "JSON")
    private String universityInfo;

    @Column(columnDefinition = "JSON")
    private String studentCard;

    @Column(columnDefinition = "JSON")
    private String certification;

    @Column(columnDefinition = "JSON")
    private String familyStatus;

    private Long assets;

    private Boolean criminalRecord;

    private Integer healthStatus; // 점수 (1~100)

    private Boolean gender; // 성별

    @Column(length = 255)
    private String address;

    private Integer mentalStatus; // 점수

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
