package com.shinhan.peoch.payment.entity;

import com.shinhan.peoch.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refunds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refundId;

    @OneToOne
    @JoinColumn(name = "paymentId")
    PaymentEntity payment;

    private Long amount;
    private LocalDateTime date;

}
