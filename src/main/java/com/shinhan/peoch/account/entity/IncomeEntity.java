package com.shinhan.peoch.account.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "소득정보관련") // 실제 DB 테이블명이 "소득정보관련"일 경우
public class IncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_id")
    private Long incomeId;

    // 어떤 사용자(user) 소득인지 식별하기 위한 컬럼
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 연봉(또는 월급) 정보
    private Integer salary;

    // 연도 (ex: 2025)
    private Integer year;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 청구(BillEntity)와의 1:N 관계를 양방향으로 매핑하고 싶다면,
     * 아래처럼 @OneToMany를 둘 수 있습니다. (선택 사항)
     */
    @OneToMany(mappedBy = "income", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<BillEntity> bills = new ArrayList<>();

    // 편의 메서드 (양방향 관계 시, 연관관계 편의 설정)
    public void addBill(BillEntity bill) {
        bills.add(bill);
        bill.setIncome(this);
    }
}
