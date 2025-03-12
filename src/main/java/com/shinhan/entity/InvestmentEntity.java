package com.shinhan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "investment")
public class InvestmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Integer grantId;

    @Column(name = "user_id")
     Integer userId;

    @Column(nullable = false, columnDefinition = "JSON")
     String expectedIncome; // 예상 수익 (JSON 형식)

    @Column(nullable = false)
     LocalDate startDate; // 투자 시작 날짜

    @Column(nullable = false)
     LocalDate endDate; // 투자 종료 날짜

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
     InvestmentStatus status; // 투자 상태 (ENUM: ACTIVE, INACTIVE 등)

     Long originalInvestValue; // 원금 투자 금액

     Integer monthlyAllowance; // 월별 수당

     Boolean isActive; // 활성화 여부

     Double refundRate; // 환급 비율 (%)

     Integer maxInvestment; // 최대 투자 금액

    @Column(columnDefinition = "JSON")
     String field; // 투자 분야 (JSON 형식)

     Long investValue; // 현재 투자 금액

     Integer tempAllowance; // 임시 수당

    @CreationTimestamp
     LocalDateTime createdAt; // 생성 시간 자동 기록

    @UpdateTimestamp
     LocalDateTime updatedAt; // 업데이트 시간 자동 기록

}
