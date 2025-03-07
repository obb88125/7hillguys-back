package com.shinhan.entity;

import java.io.Serializable;

public class MyBenefitId implements Serializable {
    private Long benefitId;
    private Long cardId;

    public MyBenefitId() {}

    public MyBenefitId(Long benefitId, Long cardId) {
        this.benefitId = benefitId;
        this.cardId = cardId;
    }

}
