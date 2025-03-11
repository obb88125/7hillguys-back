//package com.shinhan.peoch.invest.service;
//
//import com.shinhan.entity.InvestmentEntity;
//import com.shinhan.repository.InvestmentRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class InvestmentService {
//    private final InvestmentRepository investmentRepository;
//
//    @Transactional(ReadOnly=true)
//    public String getInvestmentStatus(Integer grantId) {
//        Optional<InvestmentEntity> investment = investmentRepository.findById(grantId);
//
//        if(investment.isPresent()) {
//            switch(investment.get().getStatus()) {
//                case 승인:
//                    return "approved";
//                case 거절:
//                    return "rejected";
//                case 대기:
//                    return "pending";
//            }
//        } else {
//            throw new IllegalArgumentException("투자 정보를 찾을 수 없습니다.");
//        }
//    }
//}
