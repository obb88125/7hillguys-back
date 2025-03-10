package com.shinhan.peoch.payment;

import com.shinhan.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Optional;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private boolean success;   // 결제 성공 여부
    private String message;    // 응답 메시지 = "결제가 승인되었습니다.", "유효하지 않은 카드입니다.", "월 한도를 초과했습니다.", "유효하지 않은 상점입니다."
    private String code;  // "APPROVED" = 결제 승인, "NOT_VALID_CARD" = 유효하지 않은 카드, "LIMIT_EXCEEDED" = 한도 초과, "NOT_VALID_STORE" = 유효하지 않은 상점

}
