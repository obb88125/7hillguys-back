package com.shinhan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "expected_income")
public class ExpectedIncomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer grantId;

    @Column(nullable = false)
    private Integer userProfileId; // 외래 키로 설정 가능

    @Column(columnDefinition = "JSON", nullable = false)
    private String expectedIncome; // 생애주기 총소득 JSON

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
