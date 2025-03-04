package com.shinhan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "cards")
public class CardsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 50)
    private String cardNumber;

    @Column(length = 20)
    private String enName;

    @Column(length = 50)
    private String password;

    @Column(length = 50)
    private String cvc;

    @Column(nullable = false, length = 50)
    private String issuedDate;

    @Column(nullable = false, length = 50)
    private String expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CardStatus status; // PENDING_APPROVAL, ACTIVE, INACTIVE

    private Integer monthlyAllowance;

    private Integer tempAllowance;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}