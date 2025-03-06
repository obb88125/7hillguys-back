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
    private Integer grantId; // Primary Key

    @Column(nullable = false)
    private Integer userId; // UserEntity와의 외래 키 관계

    @Column(nullable = false, columnDefinition = "JSON")
    private String expectedIncome; // 예상 수익 (JSON 형식)

    @Column(nullable = false)
    private LocalDate startDate; // 투자 시작 날짜

    @Column(nullable = false)
    private LocalDate endDate; // 투자 종료 날짜

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InvestmentStatus status; // 투자 상태 (ENUM: ACTIVE, INACTIVE 등)

    private Long originalInvestValue; // 원금 투자 금액

    private Integer monthlyAllowance; // 월별 수당

    private Boolean isActive; // 활성화 여부

    private Double refundRate; // 환급 비율 (%)

    private Integer maxInvestment; // 최대 투자 금액

    @Column(columnDefinition = "JSON")
    private String field; // 투자 분야 (JSON 형식)

    private Long investValue; // 현재 투자 금액

    private Integer tempAllowance; // 임시 수당

    @CreationTimestamp
    private LocalDateTime createdAt; // 생성 시간 자동 기록

    @UpdateTimestamp
    private LocalDateTime updatedAt; // 업데이트 시간 자동 기록
}
