package com.shinhan.peoch.lifecycleincome.service;

import com.shinhan.entity.InvestmentEntity;
import com.shinhan.entity.PaymentEntity;
import com.shinhan.peoch.lifecycleincome.DTO.ExitResponseDTO;
import com.shinhan.repository.InvestmentRepository;
import com.shinhan.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 그냥 수익률 연산
     * 전체 쓴돈을 년을 기준으로 현재까지 기간 만큼 1.15^n년 연산
     *  사실 청구서가 있어야하는데???
     *  내야 될 돈에서 낸 만큼을 제해야 exit비용인데
     * @param userId
     * @return long exitvalue
     */
    public long calculateExitCost(long userId){
        List<PaymentEntity> paymentList = paymentRepository.findByCard_User_UserId(userId);

        return 0;
    }
}
