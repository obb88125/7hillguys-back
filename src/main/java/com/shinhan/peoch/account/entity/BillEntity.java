package com.shinhan.peoch.account.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bill")
public class BillEntity {
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long billId;

    private LocalDate date;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 투자 청구 금액
    @Column(name = "invest_charge_amount")
    private Integer investChargeAmount;

    // 납부 상태 (ex: 미납, 완납, 부분납)
    @Column(name = "invest_payment_status")
    private String investPaymentStatus;

    // 실제 납부된 금액
    @Column(name = "invest_payment_amount")
    private Integer investPaymentAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "income_id")
    private IncomeEntity income;
}
