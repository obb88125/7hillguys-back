package com.shinhan.peoch.lifecycleincome.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class NormUserProfileEntity {

    @Id
    private int userProfileId;

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters
}
