package com.shinhan.peoch.card;

import com.shinhan.entity.CardEntity;
import com.shinhan.entity.PaymentEntity;
import com.shinhan.entity.RefundEntity;
import com.shinhan.repository.CardRepository;
import com.shinhan.repository.PaymentRepository;
import com.shinhan.repository.RefundRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;

    public CardService(CardRepository cardRepository, PaymentRepository paymentRepository, RefundRepository refundRepository) {
        this.cardRepository = cardRepository;
        this.paymentRepository = paymentRepository;
        this.refundRepository = refundRepository;
    }

    // 카드 명세서
    public CardStatementResponseDTO getCardStatement(Long userId, String yearMonth) {
        // yearMonth = String("yyyy-MM"), yearMonth가 없으면 현재 연도/월을 사용
        YearMonth ym;
        if (yearMonth == null || yearMonth.isEmpty()) {
            ym = YearMonth.now();
        } else {
            ym = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
        }
        // 해당 월의 시작일과 종료일 계산
        LocalDateTime startDate = ym.atDay(1).atStartOfDay();
        LocalDateTime endDate = ym.atEndOfMonth().atTime(23, 59, 59, 999999999);

        List<PaymentEntity> payments = paymentRepository.findByCard_User_UserIdAndDateBetween(userId, startDate, endDate);
        List<RefundEntity> refunds = refundRepository.findByPayment_Card_User_UserIdAndDateBetween(userId, startDate, endDate);
        List<CardStatementDTO> statementList = new ArrayList<>();

        for (PaymentEntity payment : payments) {
            CardStatementDTO dto = convertToCardStatementDTO(payment);
            statementList.add(dto);
        }

        for (RefundEntity refund : refunds) {
            CardStatementDTO dto = convertRefundToDTO(refund);
            statementList.add(dto);
        }

        // 결제일 내림차순(가장 최신 거래가 맨 앞)
        statementList.sort(Comparator.comparing(CardStatementDTO::getPaymentDate).reversed());


        CardEntity card = cardRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저의 카드가 존재하지 않습니다. userId: " + userId));
        Integer monthlySpent = card.getMonthlySpent();
        Integer monthlyAllowance = card.getMonthlyAllowance();

        CardStatementResponseDTO response = new CardStatementResponseDTO();
        response.setStatementList(statementList);
        response.setMonthlyAllowance(monthlyAllowance);
        response.setMonthlySpent(monthlySpent);

        return response;
    }

    // 결제완료, 할부 내역 CardStatementDTO로 변환
    private CardStatementDTO convertToCardStatementDTO(PaymentEntity payment) {
        CardStatementDTO dto = new CardStatementDTO();
        dto.setAmount(payment.getFinalAmount());
        dto.setPaymentDate(payment.getDate());
        dto.setStoreName(payment.getStore().getName());

        // status에 따라 분기 처리
        switch (payment.getStatus()) {
            case PENDING:
                dto.setPaymentStatus("PENDING");
                dto.setInstallmentMonth(payment.getInstallmentMonth() != null ? payment.getInstallmentMonth() : 0);
                dto.setInstallmentRound(payment.getInstallmentRound() != null ? payment.getInstallmentRound() : 0);
                dto.setBenefitDiscountAmount(payment.getDiscountAmount());
                break;
            case PAID:
                dto.setPaymentStatus("PAID");
                dto.setInstallmentMonth(0);
                dto.setInstallmentRound(0);
                dto.setBenefitDiscountAmount(payment.getDiscountAmount());
                break;
        }

        return dto;
    }

    // 환불 내역 CardStatementDTO로 변환
    private CardStatementDTO convertRefundToDTO(RefundEntity refund) {
        CardStatementDTO dto = new CardStatementDTO();
        dto.setAmount(refund.getAmount());
        dto.setPaymentDate(refund.getDate());
        dto.setPaymentStatus("REFUNDED");
        dto.setStoreName(refund.getPayment().getStore().getName());
        dto.setInstallmentMonth(0);
        dto.setInstallmentRound(0);
        dto.setBenefitDiscountAmount(0L);
        return dto;
    }


    // 내 카드 실적/혜택
    public List<CardPerformanceDTO> getCardPerformance(Long userId, Integer month) {
        // 현재 연도 사용
        int currentYear = LocalDate.now().getYear();
        int targetMonth = (month == null) ? LocalDate.now().getMonthValue() : month;
        YearMonth ym = YearMonth.of(currentYear, targetMonth);
        LocalDateTime startDate = ym.atDay(1).atStartOfDay();
        LocalDateTime endDate = ym.atEndOfMonth().atTime(23, 59, 59, 999999999);

        List<PaymentEntity> payments = paymentRepository.findByCard_User_UserIdAndDateBetween(userId, startDate, endDate);
        List<CardPerformanceDTO> performanceList = new ArrayList<>();
        long totalBenefitDiscount = 0;
        String userName = null;

        for (PaymentEntity p : payments) {
            CardPerformanceDTO dto = convertToCardPerformanceDTO(p);
            performanceList.add(dto);
            totalBenefitDiscount += (p.getDiscountAmount() != null ? p.getDiscountAmount() : 0);
            userName = p.getCard().getUser().getName(); // 같은 유저이므로 마지막 값으로 설정해도 됨
        }

        // 모든 DTO에 총 할인 혜택 금액과 유저 이름을 설정 (유저가 동일하다고 가정)
        for (CardPerformanceDTO dto : performanceList) {
            dto.setTotalBenefitDiscount(totalBenefitDiscount);
            dto.setUserName(userName);
        }

        // 결제일 내림차순(가장 최신 거래가 맨 앞)
        performanceList.sort(Comparator.comparing(CardPerformanceDTO::getPaymentDate).reversed());
        return performanceList;
    }

    // CardPerformanceDTO로 변환
    private CardPerformanceDTO convertToCardPerformanceDTO(PaymentEntity payment) {
        CardPerformanceDTO dto = new CardPerformanceDTO();
        dto.setUserName(payment.getCard().getUser().getName());
        dto.setStoreName(payment.getStore().getName());
        dto.setPaymentDate(payment.getDate());
        dto.setCardNumber(payment.getCard().getCardNumber());
        dto.setOriginalAmount(payment.getOriginalAmount());
        dto.setFinalAmount(payment.getFinalAmount());
        dto.setBenefitDiscountAmount(payment.getDiscountAmount());
        return dto;
    }

}
