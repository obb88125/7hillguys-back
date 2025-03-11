package com.shinhan.peoch.lifecycleincome.service;

import com.shinhan.entity.ExpectedIncomeEntity;
import com.shinhan.entity.InflationRateEntity;
import com.shinhan.entity.InvestmentEntity;
import com.shinhan.peoch.lifecycleincome.DTO.SetInvestAmountDTO;
import com.shinhan.repository.ExpectedIncomeRepository;
import com.shinhan.repository.InflationRateRepository;
import com.shinhan.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetInvestAmountService {

    private final InvestmentRepository investmentRepository;
    private final ExpectedIncomeRepository expectedIncomeRepository;
    private final InflationRateRepository inflationRateRepository;

    public SetInvestAmountDTO getInvestmentData(Integer userProfileId) {
        // 1. 투자 정보 조회
        InvestmentEntity investment = investmentRepository.findFirstByUserIdOrderByUpdatedAtDesc(userProfileId);

        // 2. 예상 소득 데이터 조회
        ExpectedIncomeEntity incomes = expectedIncomeRepository.findFirstByUserProfileIdOrderByUpdatedAtDesc(userProfileId);


        // 3. 물가상승률 데이터 조회 (2025년 고정)
        InflationRateEntity inflationRateEntity = inflationRateRepository.findByYear(2025);


        return SetInvestAmountDTO.builder()
                .maxInvestment(Long.valueOf(investment.getMaxInvestment()))
                .expectedIncomes(incomes.getExpectedIncome())
                .inflationRate(inflationRateEntity.getInflationRate())
                .build();
    }
}
