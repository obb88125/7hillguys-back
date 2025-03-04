package com.shinhan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "payments")
public class PaymentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    // 외래 키 관계 설정 가능 (Cards, Stores, Benefits 테이블과 연결)
    private Integer cardId;

    private Integer storeId;

    private Integer benefitId;

    private Long originalAmount;

    private Long discountAmount;

    private Long finalAmount;

    private LocalDateTime date;

    // ENUM 타입 처리 (MariaDB ENUM은 문자열로 저장 권장)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // PAID, PENDING, REFUNDED

    private Integer installmentMonth;

    private Integer installmentRound;

    private String currency;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // 업데이트 시간 자동 관리
}
