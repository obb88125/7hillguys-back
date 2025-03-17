package com.shinhan.peoch.card;

import com.shinhan.peoch.auth.entity.UserEntity;
import com.shinhan.repository.PaymentRepository;
import com.shinhan.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

@Service
public class CardDataService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public CardDataService(PaymentRepository paymentRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    // 카드 데이터
    public CardDataResponseDTO getCardData(Long userId, String requestDate) {
        // ***** 나의 카드 데이터 *****
        LocalDate date = LocalDate.parse(requestDate);

        // 주 데이터 계산
        // 현재 주: 조회일 기준 해당 주의 월요일부터 조회일까지의 기간
        LocalDate currentWeekStart = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        int daysCount = date.getDayOfWeek().getValue(); // 월요일=1, ... 일요일=7

        // 이전 주: 현재 주 시작일에서 1주 전의 월요일부터 동일한 일수만큼 계산
        LocalDate previousWeekStart = currentWeekStart.minusWeeks(1);
        LocalDate previousWeekEnd = previousWeekStart.plusDays(daysCount - 1);

        // 월 데이터 계산
        // 현재 달: 조회일의 1일부터 조회일까지
        LocalDate currentMonthStart = date.withDayOfMonth(1);
        int currentDayOfMonth = date.getDayOfMonth();

        // 이전 달: 조회일의 일자가 존재하면 해당 일자, 없으면 이전 달 전체
        LocalDate previousMonthDate = date.minusMonths(1);
        LocalDate previousMonthStart = previousMonthDate.withDayOfMonth(1);
        int previousMonthLength = previousMonthDate.lengthOfMonth();
        LocalDate previousMonthEnd = (currentDayOfMonth <= previousMonthLength)
                ? previousMonthDate.withDayOfMonth(currentDayOfMonth)
                : previousMonthDate.withDayOfMonth(previousMonthLength);

        // 연 데이터 계산
        // 현재 연: 해당 연도의 1월 1일부터 조회일까지
        LocalDate currentYearStart = LocalDate.of(date.getYear(), 1, 1);
        // 이전 연: 이전 연도의 1월 1일부터 조회일과 동일한 월, 일로 계산
        LocalDate previousYearDate = date.minusYears(1);
        LocalDate previousYearStart = LocalDate.of(previousYearDate.getYear(), 1, 1);
        LocalDate previousYearEnd = LocalDate.of(previousYearDate.getYear(), date.getMonthValue(), date.getDayOfMonth());

        // 기간 별 총 사용 금액 계산
        int weekCurrent = getTotalFinalAmount(userId, currentWeekStart, date);
        int weekPrevious = getTotalFinalAmount(userId, previousWeekStart, previousWeekEnd);
        int monthCurrent = getTotalFinalAmount(userId, currentMonthStart, date);
        int monthPrevious = getTotalFinalAmount(userId, previousMonthStart, previousMonthEnd);
        int yearCurrent = getTotalFinalAmount(userId, currentYearStart, date);
        int yearPrevious = getTotalFinalAmount(userId, previousYearStart, previousYearEnd);

        // ***** 평균 카드 데이터 계산 *****
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        int age = Period.between(user.getBirthdate(), LocalDate.now()).getYears();

        // 평균의 기간 별 총 사용 금액 계산
        int avgWeekCurrent = getAverageTotalFinalAmount(age, currentWeekStart, date);
        int avgWeekPrevious = getAverageTotalFinalAmount(age, previousWeekStart, previousWeekEnd);
        int avgMonthCurrent = getAverageTotalFinalAmount(age, currentMonthStart, date);
        int avgMonthPrevious = getAverageTotalFinalAmount(age, previousMonthStart, previousMonthEnd);
        int avgYearCurrent = getAverageTotalFinalAmount(age, currentYearStart, date);
        int avgYearPrevious = getAverageTotalFinalAmount(age, previousYearStart, previousYearEnd);

        // DTO 반환
        return new CardDataResponseDTO(
                weekCurrent,
                weekPrevious,
                monthCurrent,
                monthPrevious,
                yearCurrent,
                yearPrevious,
                avgWeekCurrent,
                avgWeekPrevious,
                avgMonthCurrent,
                avgMonthPrevious,
                avgYearCurrent,
                avgYearPrevious
        );
    }

    // 해당 기간 총 사용 금액 조회
    private int getTotalFinalAmount(Long useId, LocalDate start, LocalDate end) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);
        return paymentRepository.findTotalFinalAmountByUserAndDateBetween(useId, startDateTime, endDateTime);
    }

    // 동일한 나이의 사용자들에 대한 평균 카드 사용 금액 조회
    private int getAverageTotalFinalAmount(int age, LocalDate start, LocalDate end) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);
        Double avgTotal = paymentRepository.findAverageTotalFinalAmountByUserAgeAndDateBetween(age, startDateTime, endDateTime);

        return (avgTotal != null) ? avgTotal.intValue() : 0;
    }

}
