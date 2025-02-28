package com.shinhan.peoch.payment.entity;

import com.shinhan.peoch.benefit.entity.BenefitEntity;
import com.shinhan.peoch.card.entity.CardEntity;
import com.shinhan.peoch.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PaymentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Long original_amount;
    private Long discount_amount;
    private Long final_amount;
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Integer installment_month;
    private Integer installment_round;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "cardId")
    CardEntity card;

    @ManyToOne
    @JoinColumn(name = "storeId")
    StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "benefitId")
    BenefitEntity benefit;

}
