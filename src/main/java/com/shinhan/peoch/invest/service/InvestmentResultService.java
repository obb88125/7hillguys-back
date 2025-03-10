package com.shinhan.peoch.invest.service;

import com.shinhan.entity.InvestmentEntity;
import com.shinhan.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvestmentResultService {
    private final InvestmentRepository investmentRepository;

    @Transactional(readOnly = true)
    public String getInvestmentStatus(Integer grantId) {
        Optional<InvestmentEntity> investment = investmentRepository.findById(grantId);

        if (investment.isPresent()) {
            switch (investment.get().getStatus()) {
                case 승인:
                    return "approved"; // 승인된 경우
                case 거절:
                    return "rejected"; // 거절된 경우
                case 대기:
                default:
                    return "pending"; // 대기 중인 경우
            }
        } else {
            throw new IllegalArgumentException("투자 정보를 찾을 수 없습니다.");
        }
    }
}
