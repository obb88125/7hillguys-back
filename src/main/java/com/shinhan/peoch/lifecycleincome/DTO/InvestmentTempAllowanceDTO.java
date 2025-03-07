package com.shinhan.peoch.lifecycleincome.DTO;

import com.shinhan.entity.ExpectedIncomeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InvestmentTempAllowanceDTO {
    private long availableAmount; // 현재 지원 가능 금액
    private long investValue;     // 총 지원 금액
    private double progress;      // 진행률
    private double expectedIncome;  // 예상 수익
    private double refundRate; // 반환 비율
    private List<ExpectedIncomeEntity> incomes;
}
