package com.shinhan.peoch.lifecycleincome.service;

import com.shinhan.entity.ExpectedIncomeEntity;
import com.shinhan.entity.InflationRateEntity;
import com.shinhan.entity.InvestmentEntity;
import com.shinhan.entity.UserProfileEntity;
import com.shinhan.peoch.lifecycleincome.DTO.SetInvestAmountDTO;
import com.shinhan.repository.ExpectedIncomeRepository;
import com.shinhan.repository.InflationRateRepository;
import com.shinhan.repository.InvestmentRepository;
import com.shinhan.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SetInvestAmountService {
    @Autowired
    InvestmentRepository investmentRepository;
    @Autowired
    ExpectedIncomeRepository expectedIncomeRepository;
    @Autowired
    InflationRateRepository inflationRateRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    public SetInvestAmountDTO getInvestmentData(Integer userProfileId) {
        UserProfileEntity userProfileEntity = userProfileRepository.findByUserProfileId(userProfileId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저 프로필 정보를 찾을 수 없습니다."));
        // 1. 투자 정보 조회
        InvestmentEntity investment = investmentRepository.findFirstByUserIdOrderByUpdatedAtDesc(userProfileEntity.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("해당 투자 정보를 찾을 수 없습니다."));
        System.out.println(investment);
        // 2. 예상 소득 데이터 조회
        ExpectedIncomeEntity incomes = expectedIncomeRepository.findFirstByUserProfileIdOrderByCreatedAtDesc(userProfileId).
        orElseThrow(() -> new RuntimeException("사용자 예상 소득 정보를 찾을 수 없습니다."));


        // 3. 물가상승률 데이터 조회 (2025년 고정)
        InflationRateEntity inflationRateEntity = inflationRateRepository.findByYear(LocalDate.now().getYear());


        return SetInvestAmountDTO.builder()
                .maxInvestment(Long.valueOf(investment.getMaxInvestment()))
                .expectedIncomes(incomes.getExpectedIncome())
                .inflationRate(inflationRateEntity.getInflationRate())
                .build();
    }
}
