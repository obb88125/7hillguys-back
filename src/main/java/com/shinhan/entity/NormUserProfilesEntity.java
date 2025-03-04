package com.shinhan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "norm_user_profiles")
public class NormUserProfilesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userProfileId;

    private Integer university;

    private Integer educationMajor;

    private Integer certification;

    private Integer familyStatus;

    private Integer assets;

    private Integer criminalRecord;

    private Integer healthStatus;

    private Integer gender;

    private Integer address;

    private Integer mentalStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
