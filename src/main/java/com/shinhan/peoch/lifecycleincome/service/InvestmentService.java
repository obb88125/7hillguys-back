package com.shinhan.peoch.lifecycleincome.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinhan.entity.*;
import com.shinhan.peoch.auth.entity.UserEntity;
import com.shinhan.peoch.auth.service.UserService;
import com.shinhan.peoch.lifecycleincome.DTO.InvestmentTempAllowanceDTO;
import com.shinhan.repository.ExpectedIncomeRepository;
import com.shinhan.repository.InflationRateRepository;
import com.shinhan.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    private final double rateofreturn = 0.15;
    @Autowired
    ExpectedIncomeRepository expectedIncomeRepository;
    @Autowired
    InflationRateRepository inflationRateRepository;
    @Autowired
    ExpectedValueService expectedValueService;
    @Autowired
    InvestmentRepository investmentRepository;
    @Autowired
    UserService userService;
    @Autowired
    ExpectedIncomeService expectedIncomeService;
    // 투자 정보 저장
    public InvestmentEntity saveInvestment(InvestmentEntity investment) {
        return investmentRepository.save(investment);
    }

    // 특정 투자 정보 조회 (ID로 조회)
    public Optional<InvestmentEntity> findInvestmentById(Integer grantId) {
        return investmentRepository.findById(grantId);
    }

    // 모든 투자 정보 조회
    public List<InvestmentEntity> findAllInvestments() {
        return investmentRepository.findAll();
    }

    // 투자 정보 업데이트(사용 X)
    public InvestmentEntity updateInvestment(Integer grantId, InvestmentEntity updatedInvestment) {
        return investmentRepository.findById(grantId).map(investment -> {
            investment.setUserId(updatedInvestment.getUserId());
            investment.setExpectedIncome(updatedInvestment.getExpectedIncome());
            investment.setStartDate(updatedInvestment.getStartDate());
            investment.setEndDate(updatedInvestment.getEndDate());
            investment.setStatus(updatedInvestment.getStatus());
            investment.setOriginalInvestValue(updatedInvestment.getOriginalInvestValue());
            investment.setMonthlyAllowance(updatedInvestment.getMonthlyAllowance());
            investment.setIsActive(updatedInvestment.getIsActive());
            investment.setRefundRate(updatedInvestment.getRefundRate());
            investment.setMaxInvestment(updatedInvestment.getMaxInvestment());
            investment.setField(updatedInvestment.getField());
            investment.setInvestValue(updatedInvestment.getInvestValue());
            investment.setTempAllowance(updatedInvestment.getTempAllowance());
            return investmentRepository.save(investment);
        }).orElseThrow(() -> new IllegalArgumentException("해당 투자 정보를 찾을 수 없습니다. ID: " + grantId));
    }

    // 투자 정보 삭제? 사용 X active false로 사용할것
    public void deleteInvestment(Integer userId) {
        if (investmentRepository.existsById(userId)) {
            investmentRepository.deleteById(userId);
        } else {
            throw new IllegalArgumentException("해당 투자 정보를 찾을 수 없습니다. ID: " + userId);
        }
    }
    public InvestmentEntity createInvestment(Integer userId) {
        // 예상 소득 데이터 가져오기
        List<ExpectedIncomeEntity> incomeEntities = expectedIncomeRepository.findByUserProfileId(userId);
        if (incomeEntities.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        // 가장 최근의 예상 소득 데이터 사용
        ExpectedIncomeEntity latestIncomeEntity = incomeEntities.get(incomeEntities.size() - 1);
        Map<Integer, Double> expectedIncome = parseJsonToMap(latestIncomeEntity.getExpectedIncome());

        // 국채 수익률 데이터 가져오기
        InflationRateEntity inflationRateEntity = inflationRateRepository.findByYear(2025);
        Map<Integer, Double> inflationRates = parseJsonToMap(inflationRateEntity.getInflationRate());

        // 현재 가치 계산
        double totalPresentValue = 0.0;
        for (Map.Entry<Integer, Double> entry : expectedIncome.entrySet()) {
            int year = entry.getKey();
            double income = entry.getValue();
            double discountRate = getDiscountRate(year, inflationRates);
            totalPresentValue += calculatePresentValueForIncome(income, year, discountRate);
        }
        // 나이 가져와!
        UserEntity user = userService.getUserById(Long.valueOf(userId));
        // InvestmentEntity 생성 및 저장
        // 연 수익률 계산
        LocalDate endDate = calculateEndDate(user.getBirthdate());
        // 3. 연 수익률 계산
        double annualizedReturnRate = calculateAnnualizedReturnRate(
                LocalDate.now(),  // 시작일: 현재 날짜
                endDate,          // 종료일: 65세 생일
                rateofreturn
        );
        // InvestmentEntity 생성 및 저장
        InvestmentEntity investment = InvestmentEntity.builder()
                .userId(userId)
                .expectedIncome(latestIncomeEntity.getExpectedIncome())
                .status(InvestmentStatus.대기) // 승인 대기중 상태
                .originalInvestValue(0L)
                .monthlyAllowance(0)
                .isActive(false)
                .maxInvestment((int)((totalPresentValue)*(0.2)/(1+annualizedReturnRate))) // 연 수익률 적용
                .investValue(0L)
                .refundRate(0.0)
                .tempAllowance(0)
                .startDate(LocalDate.now()) // 투자 시작 날짜를 임시로 설정(투자 승인날에 업데이트 해줘야함)
                .endDate(LocalDate.now()) // 사용자가 정한 투자 기간 최대 10년
                .build();

        return investmentRepository.save(investment);
    }

    private double calculatePresentValueForIncome(double income, int year, double discountRate) {
        return income / Math.pow(1 + discountRate / 100, year);
    }

    private double getDiscountRate(int year, Map<Integer, Double> inflationRates) {
        if (year <= 5) return inflationRates.getOrDefault(year, inflationRates.get(5));
        if (year <= 9) return inflationRates.get(5);
        if (year <= 19) return inflationRates.get(10);
        if (year <= 29) return inflationRates.get(20);
        if (year <= 49) return inflationRates.get(30);
        return inflationRates.get(50);
    }

    private Map<Integer, Double> parseJsonToMap(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString, new TypeReference<Map<Integer, Double>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    public double updateRefundRate(Integer userId) {
        InvestmentEntity investment = investmentRepository.findFirstByUserIdOrderByUpdatedAtDesc(userId);

        UserEntity user = userService.getUserById(Long.valueOf(userId));
        // InvestmentEntity 생성 및 저장
        // 연 수익률 계산
        LocalDate endDate = calculateEndDate(user.getBirthdate());
        // 연 수익률 계산
        double annualizedReturnRate = calculateAnnualizedReturnRate(investment.getStartDate(), endDate, rateofreturn);

        double presentValue = expectedValueService.calculatePresentValue(userId);
        double refundRate = ((investment.getOriginalInvestValue() * (1+annualizedReturnRate)) / presentValue * 100);
        refundRate = Math.round(refundRate * 1000) / 1000.0;

        investment.setRefundRate(refundRate);
        investmentRepository.save(investment);

        return refundRate;
    }

    /**
     * 투자액과 최대 투자 금액을 주면
     * 환급 비율을 돌려줌
     * @param investAmount,maxInvestment
     * @return
     */
    public double checkRefundRate(Integer userId,Integer investAmount) {
        InvestmentEntity investment = investmentRepository.findFirstByUserIdOrderByUpdatedAtDesc(userId);

        UserEntity user = userService.getUserById(Long.valueOf(userId));
        // InvestmentEntity 생성 및 저장
        // 연 수익률 계산
        LocalDate endDate = calculateEndDate(user.getBirthdate());
        // 연 수익률 계산
        double annualizedReturnRate = calculateAnnualizedReturnRate(investment.getStartDate(), endDate, rateofreturn);

        double presentValue = expectedValueService.calculatePresentValue(userId);
        double refundRate = ((investAmount* (1+annualizedReturnRate)) / presentValue * 100);
        refundRate = Math.round(refundRate * 1000) / 1000.0;

        investment.setRefundRate(refundRate);
        investmentRepository.save(investment);

        return refundRate;
    }
    // 연 수익률 계산 메서드
    private double calculateAnnualizedReturnRate(LocalDate startDate, LocalDate endDate, double rateOfReturn) {
        long months = ChronoUnit.MONTHS.between(startDate, endDate);
        if (months == 0) throw new IllegalArgumentException("투자 기간은 1개월 이상입니다.");
        double years = (double) months / 12;
        return Math.pow(1 + rateOfReturn, 1 / years) - 1;
    }
    /**
     * 사용자 월 소득과 환급 비율을 기반으로 계산된 환급 금액 반환
     *
     * @param userId            사용자 ID
     * @param userMonthlyIncome 사용자 월 소득
     * @return 계산된 환급 금액
     */
    public int calculateRefundAmount(Integer userId, int userMonthlyIncome) {
        // 투자 정보 가져오기
        InvestmentEntity investment = investmentRepository.findFirstByUserIdOrderByUpdatedAtDesc(userId);

        // 환급 비율 가져오기
        double refundRate = investment.getRefundRate();
        if (refundRate <= 0) {
            throw new IllegalArgumentException("유효하지 않은 환급 비율입니다. 사용자 ID: " + userId);
        }

        // 월 소득에 환급 비율을 곱하여 계산
        int refundAmount = (int) Math.round(userMonthlyIncome * refundRate);

        return refundAmount;
    }
    public InvestmentTempAllowanceDTO calculateInvestmentDetails(Integer userId) {
        // 투자 데이터 조회
        InvestmentEntity investment = investmentRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid grant ID"));

        LocalDate startDate = investment.getStartDate();
        LocalDate endDate = investment.getEndDate();
        long maxTotalInvestment = investment.getMaxInvestment();
        long investValue = investment.getInvestValue();

        // 오늘 날짜 기준 진행률 계산
        double progress = calculateInvestmentProgress(startDate, endDate);

        // 현재 지원 가능 금액 계산
        long availableAmount = (long) (maxTotalInvestment * progress);

        // 예상 생애 총소득 총액
        double expectedIncome = expectedValueService.calculatePresentValue(userId);

        double refundRate = updateRefundRate(userId);
        List<ExpectedIncomeEntity> incomes = expectedIncomeService.getExpectedIncomesByUserProfileId(investment.getUserId());
        // 결과 반환
        return new InvestmentTempAllowanceDTO(availableAmount, investValue, progress, expectedIncome, refundRate, incomes);
    }

    private double calculateInvestmentProgress(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();

        if (today.isBefore(startDate)) return 0.0; // 아직 시작 전
        if (today.isAfter(endDate)) return 1.0; // 이미 종료됨

        // 총 기간 (개월 단위)
        long totalMonths = ChronoUnit.MONTHS.between(startDate, endDate) + 1; // 포함 관계를 위해 +1
        // 경과 기간 (개월 단위)
        long elapsedMonths = ChronoUnit.MONTHS.between(startDate, today) + 1;

        return (double) elapsedMonths / totalMonths; // 진행률 (0~1)
    }
    private LocalDate calculateEndDate(LocalDate birthDate) {
        LocalDate sixtyFifthBirthday = birthDate.plusYears(65);
        // 생일이 현재 날짜보다 이전인 경우 다음 해로 조정 (예: 2025-03-10 생일 → 2055-03-10)
        if (sixtyFifthBirthday.isBefore(LocalDate.now())) {
            sixtyFifthBirthday = sixtyFifthBirthday.plusYears(1);
        }
        return sixtyFifthBirthday;
    }

}
