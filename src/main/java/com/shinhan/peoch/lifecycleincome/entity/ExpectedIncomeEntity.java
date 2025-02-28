package com.shinhan.peoch.lifecycleincome.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ExpectedIncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grantId;

    private int userProfileId;

    @Column(columnDefinition = "JSON")
    private String expectedIncome;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Long getGrantId() {
        return grantId;
    }

    public void setGrantId(Long grantId) {
        this.grantId = grantId;
    }

    public int getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(int userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getExpectedIncome() {
        return expectedIncome;
    }

    public void setExpectedIncome(String expectedIncome) {
        this.expectedIncome = expectedIncome;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
