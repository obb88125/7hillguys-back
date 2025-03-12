package com.shinhan.peoch.card.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequestDTO {
    private String englishName;
    private String pin;
    private String cardDesign;   // 카드 디자인 선택 (예: "PINK", "BLUE", "CHAMELEON")
}
