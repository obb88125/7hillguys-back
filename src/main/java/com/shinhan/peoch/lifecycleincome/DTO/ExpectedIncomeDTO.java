package com.shinhan.peoch.lifecycleincome.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpectedIncomeDTO {
    private Integer userId;
    private Integer userProfileId;
    private String expectedIncome; // JSON 형식의 문자열
}

