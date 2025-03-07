package com.shinhan.peoch.benefit;

import com.shinhan.entity.BenefitEntity;
import com.shinhan.entity.PaymentEntity;
import com.shinhan.repository.BenefitRepository;
import com.shinhan.repository.MyBenefitRepository;
import com.shinhan.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class BenefitService {

    private final BenefitRepository benefitRepository;
    private final MyBenefitRepository myBenefitRepository;
    private final PaymentRepository paymentRepository;

    public BenefitService(BenefitRepository benefitRepository,
                                 MyBenefitRepository myBenefitRepository,
                                 PaymentRepository paymentRepository) {
        this.benefitRepository = benefitRepository;
        this.myBenefitRepository = myBenefitRepository;
        this.paymentRepository = paymentRepository;
    }

    // 전체 혜택 조회
    public List<AllBenefitDTO> getAllBenefit(Long userId, Integer month) {
        // 현재 연도와 입력된 month(없으면 현재 월)를 기준으로 기간 계산
        int currentYear = LocalDate.now().getYear();
        int targetMonth = (month == null) ? LocalDate.now().getMonthValue() : month;
        YearMonth ym = YearMonth.of(currentYear, targetMonth);
        LocalDateTime startDate = ym.atDay(1).atStartOfDay();
        LocalDateTime endDate = ym.atEndOfMonth().atTime(23, 59, 59, 999999999);

        // 1. 사용자가 현재 사용 중인 혜택 ID 목록 조회
        List<Long> myBenefitIds = myBenefitRepository.findBenefitIdsByUserId(userId);

        // 2. 전체 혜택 목록 조회
        List<BenefitEntity> allBenefits = benefitRepository.findAll();

        List<AllBenefitDTO> AllBenefitList = new ArrayList<>();

        // 3. 각 혜택별로 DTO 변환 처리
        for (BenefitEntity benefit : allBenefits) {
            AllBenefitDTO dto = convertToAllBenefitDTO(userId, benefit, myBenefitIds, startDate, endDate);
            AllBenefitList.add(dto);
        }

        return AllBenefitList;
    }

    // AllBenefitDTO로 변환
    private AllBenefitDTO convertToAllBenefitDTO(Long userId, BenefitEntity benefit, List<Long> myBenefitIds,
                                                 LocalDateTime startDate, LocalDateTime endDate) {
        AllBenefitDTO dto = new AllBenefitDTO();
        dto.setBenefit(benefit);

        // 사용 중인 혜택이면 inUse=true, 놓친 혜택 금액은 0
        if (myBenefitIds.contains(benefit.getBenefitId())) {
            dto.setInUse(true);
            dto.setMissedBenefitAmount(0L);
        } else {
            dto.setInUse(false);
            dto.setMissedBenefitAmount(calculateMissedBenefit(userId, benefit, startDate, endDate));
        }

        return dto;
    }

    // 놓친 혜택 계산
    private long calculateMissedBenefit(Long userId, BenefitEntity benefit, LocalDateTime startDate, LocalDateTime endDate) {
        Long storeId = benefit.getStore().getStoreId();
        List<PaymentEntity> payments = paymentRepository.findByCard_User_UserIdAndStore_StoreIdAndDateBetween(
                userId, storeId, startDate, endDate);

        long missedAmount = 0;
        for (PaymentEntity payment : payments) {
            // 결제 원금이 혜택 적용 최소 결제액 이상일 때 혜택 적용
            if (payment.getOriginalAmount() >= benefit.getMinPayment()) {
                // 할인 금액 계산
                long discount = Math.round(payment.getOriginalAmount() * benefit.getDiscountRate());
                // 최대 할인 금액 초과 시 최대값 적용
                missedAmount += Math.min(discount, benefit.getMaxDiscount());
            }
        }
        return missedAmount;
    }

}
