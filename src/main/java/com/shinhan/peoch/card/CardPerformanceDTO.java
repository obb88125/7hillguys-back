package com.shinhan.peoch.card;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CardPerformanceDTO {
    // 카드 소유주
    private String userName;
    // 카드번호
    private String cardNumber;
    // 결제일
    private LocalDateTime paymentDate;
    // 상점 이름
    private String storeName;
    // 총 할인 금액
    private Long totalBenefitDiscount;
    // 결제액
    private Long originalAmount;
    // 할인된 결제액
    private Long finalAmount;
    // 할인 금액
    private Long benefitDiscountAmount;

}
