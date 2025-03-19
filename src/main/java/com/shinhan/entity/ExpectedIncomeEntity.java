package com.shinhan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expected_income")
public class ExpectedIncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer grantId;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "userProfileId")
    private UserProfileEntity userProfile;

    @Column(columnDefinition = "JSON", nullable = false)
    private String expectedIncome;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}


