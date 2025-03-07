package com.shinhan.peoch.payment;

import com.shinhan.entity.*;
import com.shinhan.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService {
    private final CardRepository cardRepository;
    private final RefundRepository refundRepository;
    private final BenefitRepository benefitRepository;
    private final MyBenefitRepository myBenefitRepository;
    private final PaymentRepository paymentRepository;
    private final StoreRepository storeRepository;

    public PaymentService(CardRepository cardRepository, RefundRepository refundRepository,
                          BenefitRepository benefitRepository, MyBenefitRepository myBenefitRepository,
                          PaymentRepository paymentRepository, StoreRepository storeRepository) {
        this.cardRepository = cardRepository;
        this.refundRepository = refundRepository;
        this.benefitRepository = benefitRepository;
        this.myBenefitRepository = myBenefitRepository;
        this.paymentRepository = paymentRepository;
        this.storeRepository = storeRepository;
    }

    // 카드 결제 로직
    public PaymentResponse processPayment(PosRequest request) {
        // 카드 유효성 검사
        boolean valid = validateCard(request.getCardNumber());
        if (!valid) {
            return new PaymentResponse(false, "유효하지 않은 카드입니다.", "NOT_VALID_CARD");
        }

        Optional<CardEntity> optionalCard = cardRepository.findByCardNumber(request.getCardNumber());
        CardEntity card = optionalCard.orElseThrow();// 카드엔티티
        String cardNumber = card.getCardNumber(); // 카드번호

        // 한도 검사
        if(isLimitExceeded(card, request.getAmount())) {
            return new PaymentResponse(false, "월 한도를 초과했습니다.", "LIMIT_EXCEEDED");
        }

        // 혜택 적용 검사
        List<BenefitEntity> myBenfitList = myBenefitRepository.findBenefitsByCardNumber(cardNumber);
        Map<String, Integer> map = applyBenefit(myBenfitList, request, cardNumber);
        int discountAmount = map.get("discountAmount"); // 할인 금액
        int finalAmount = map.get("finalAmount"); // 최종 결제 금액

        // 혜택 엔티티 조회
        BenefitEntity benefitEntity = null;
        Long usedBenefitId = null;
        if (map.get("benefitId") != null) {
            usedBenefitId = map.get("benefitId").longValue();
            benefitEntity = benefitRepository.findById(usedBenefitId)
                    .orElseThrow(() -> new RuntimeException("해당 혜택을 찾을 수 없습니다."));
        }

        // 상점 엔티티 조회
        StoreEntity storeEntity = storeRepository.findStoreById(request.getStoreId());

        // 결제 내역 생성
        PaymentEntity payment = PaymentEntity.builder()
                .originalAmount(request.getAmount())
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .date(LocalDateTime.now())
                .status(PaymentStatus.PAID)
                .card(card)
                .store(storeEntity)
                .benefit(usedBenefitId != null ? benefitEntity : null)
                .build();
        paymentRepository.save(payment);

        // 월 누적 사용금액 업데이트
        card.setMonthlySpent(card.getMonthlySpent() + finalAmount);
        cardRepository.save(card);

        return new PaymentResponse(true, "결제가 승인되었습니다.", "APPROVED");
    }

    // 카드 유효성 검사
    private boolean validateCard(String cardNumber) {
        Optional<CardEntity> optionalCard = cardRepository.findByCardNumber(cardNumber);

        // 카드 존재 여부 검사
        if (optionalCard.isPresent()) {
            CardEntity card = optionalCard.get();

            // 카드 상태 검사
            if (card.getStatus() != CardStatus.ACTIVE) {
                return false;
            }

            // 카드 만료일 검사
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth expYearMonth = YearMonth.parse(card.getExpirationDate(), formatter); // 카드 만료일 YearMonth 객체로 변환
            YearMonth currentYearMonth = YearMonth.now(); // 현재 연월(YearMonth)

            // 만료일 비교
            if (expYearMonth.isBefore(currentYearMonth)) {
                return false;
            }
            return true; // 유효한 카드
        } else {
            return false;
        }
    }

    // 한도 검사
    private boolean isLimitExceeded(CardEntity card, int amount) {
        int monthlyAllowance = card.getMonthlyAllowance(); // 월 한도
        int monthlySpent = card.getMonthlySpent(); // 이번 달 누적 소비금액

        return (monthlySpent + amount) > monthlyAllowance;
    }

    // 혜택 적용 검사
    public Map<String, Integer> applyBenefit(List<BenefitEntity> myBenfitList, PosRequest request, String cardNumber) {
        Long storeId = request.getStoreId(); // 포스기로부터 전달받은 상점 ID
        int amount = request.getAmount(); // 결제 금액
        int finalAmount = amount; // 최종 결제 금액
        int discountAmount = 0;
        Integer usedBenefitId = null;
        Map<String, Integer> map = new HashMap<>();

        // 카드에 등록된 혜택 목록을 순회하면서 상점 ID가 일치하는 혜택 찾기
        for (BenefitEntity benefit : myBenfitList) {
            Long benefitId = benefit.getBenefitId();
            Optional<Long> storeIdOpt = benefitRepository.findStoreIdByBenefitId(benefit.getBenefitId());
            if (storeIdOpt.isPresent()) {
                Long benefitStoreId = storeIdOpt.get(); // 혜택이 적용된 상점 ID

                // 혜택 검사
                if (benefitStoreId.equals(storeId)) {
                    // 최소 결제 금액 검사
                    if (amount < benefit.getMinPayment()) {
                        break;
                    }

                    // 월 사용 횟수 검사
                    Optional<MyBenefitEntity> myBenefitOpt = myBenefitRepository.findMyBenefitByCardNumberAndBenefitId(cardNumber, benefitId);
                    if (myBenefitOpt.isPresent()) {
                        MyBenefitEntity myBenefitEntity = myBenefitOpt.get();

                        if (benefit.getUsageLimit() < myBenefitEntity.getUsedCount()) {
                            break;
                        } else {
                            myBenefitEntity.setUsedCount(myBenefitEntity.getUsedCount() + 1); // 사용 횟수 증가
                        }
                    }

                    // 최대 할인 금액 검사
                    discountAmount = (int) (amount * (benefit.getDiscountRate() / 100.0));
                    if (discountAmount > benefit.getMaxDiscount()) {
                        discountAmount = benefit.getMaxDiscount();
                    }

                    // 혜택 적용 완료
                    usedBenefitId = benefitId.intValue();
                    map.put("benefitId", usedBenefitId);
                    finalAmount = amount - discountAmount;
                    break;
                }
            }
        }

        map.put("finalAmount", finalAmount);
        map.put("discountAmount", discountAmount);
        return map;
    }

//    // 카드 환불 로직
//    public void processRefund(RefundRequest request) {
//        Optional<Payment> paymentOpt = paymentRepository.findById(request.getPaymentId());
//        if(!paymentOpt.isPresent()){
//            System.out.println("[환불 실패] 해당 결제 기록이 존재하지 않습니다.");
//            return;
//        }
//        Payment payment = paymentOpt.get();
//        if(!payment.getCardNumber().equals(request.getCardNumber())){
//            System.out.println("[환불 실패] 카드 정보가 일치하지 않습니다.");
//            return;
//        }
//        if(!"PAID".equals(payment.getStatus())){
//            System.out.println("[환불 실패] 환불 가능한 결제가 아닙니다.");
//            return;
//        }
//        payment.setStatus("REFUNDED");
//        paymentRepository.save(payment);
//
//        Refund refund = new Refund();
//        refund.setRefundId(UUID.randomUUID().toString());
//        refund.setPaymentId(payment.getPaymentId());
//        refund.setCardNumber(request.getCardNumber());
//        refund.setRefundAmount(payment.getTotalDeduction());
//        refund.setTimestamp(LocalDateTime.now());
//        refundRepository.save(refund);
//        System.out.println("[환불 성공] Refund ID: " + refund.getRefundId());
//    }

}
