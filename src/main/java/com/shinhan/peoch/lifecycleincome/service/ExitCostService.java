package com.shinhan.peoch.lifecycleincome.service;

import com.shinhan.entity.InvestmentEntity;
import com.shinhan.peoch.lifecycleincome.DTO.ExitResponseDTO;
import com.shinhan.repository.InvestmentRepository;
import com.shinhan.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExitCostService {

    @Autowired
    InvestmentService investmentService;

    @Autowired
    InvestmentRepository investmentRepository;

    @Autowired
    PaymentRepository paymentRepository;

    public ExitResponseDTO exitResponseService(Integer userId){
        InvestmentEntity firstExpectedIncomeEntity = investmentRepository.findFirstByUserIdOrderByUpdatedAtDesc(userId);
        InvestmentEntity lastExpectedIncomeEntity = investmentRepository.findFirstByUserIdOrderByCreatedAtAsc(userId);
        ExitResponseDTO exitResponseDTO= ExitResponseDTO.builder()
                .firstExpectedIncome(firstExpectedIncomeEntity.getExpectedIncome())
                .lastExpectedIncome(lastExpectedIncomeEntity.getExpectedIncome())
                .discountAmount(paymentRepository.sumDiscountAmountByUserId(userId))
                .investValue(paymentRepository.sumFinalAmountByUserId(userId))
                .exitCost(calculateExitCost(userId))
                .build();

        return exitResponseDTO;
    }
    public long calculateExitCost(Integer userId){
        paymentRepository.sumDiscountAmountByUserId(userId);
        return 0;
    }
}
