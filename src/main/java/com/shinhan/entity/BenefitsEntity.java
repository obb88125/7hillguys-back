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
@Table(name = "benefits")
public class BenefitsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer benefitId; // Primary Key

    @Column(nullable = false)
    private Integer storeId; // Stores 테이블과의 외래 키 관계

    @Column(nullable = false, length = 255)
    private String name; // 혜택 이름

    @Column(columnDefinition = "TEXT")
    private String description; // 혜택 설명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BenefitType type; // 혜택 유형 (ENUM)

    private Float discountRate; // 할인율 (%)

    private Integer maxDiscount; // 최대 할인 금액

    private Integer minPayment; // 최소 결제 금액

    private Integer usageLimit; // 사용 제한 횟수

    @CreationTimestamp
    private LocalDateTime createdAt; // 생성 시간 자동 기록

    @UpdateTimestamp
    private LocalDateTime updatedAt; // 업데이트 시간 자동 기록
}
