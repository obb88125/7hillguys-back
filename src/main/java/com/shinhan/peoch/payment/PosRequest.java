package com.shinhan.peoch.payment;

import lombok.Data;

@Data
public class PosRequest {
    private String cardNumber;
    private Integer amount;
    private Long storeId;
}
