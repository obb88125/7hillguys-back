package com.shinhan.peoch.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class CardStatementDTO {
    // 금액
    private Long amount;
    // 결제일
    private LocalDateTime paymentDate;
    // 결제 방법
    private String paymentStatus;
    // 상점 이름
    private String storeName;
    // 할부
    private Integer installmentMonth;
    // 할부 회차
    private Integer installmentRound;
    // 혜택 할인 금액
    private Long benefitDiscountAmount;

}

