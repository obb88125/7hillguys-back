package com.shinhan.peoch.payment;

import lombok.Data;

@Data
public class PosPaymentRequest {
    private String cardNumber;
    private Integer amount;
    private Long storeId;
    private Integer installmentMonth;

}
