package com.shinhan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "refunds")
public class RefundsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer refundId;

    @Column(nullable = false)
    private Integer paymentId; // PaymentsEntity와의 외래 키 관계

    private Long amount;

    private LocalDateTime date;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
