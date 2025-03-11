package com.shinhan.peoch.lifecycleincome.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExitResponseDTO {
     String firstExpectedIncome; // JSON 가입 당시 예상치
     String lastExpectedIncome; // JSON 마지막 예상치
     long discountAmount; //할인 된 가격 총액
     long investValue;//사용한 금액
     long exitCost;
}
