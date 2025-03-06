package com.shinhan.peoch.lifecycleincome.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinhan.entity.*;
import com.shinhan.peoch.auth.service.UserService;
import com.shinhan.repository.ExpectedIncomeRepository;
import com.shinhan.repository.InflationRateRepository;
import com.shinhan.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    private final double rateofreturn = 0.15;
    @Autowired
    private ExpectedIncomeRepository expectedIncomeRepository;

    @Autowired
    private InflationRateRepository inflationRateRepository;
    @Autowired
    private ExpectedValueService expectedValueService;
    @Autowired
    private InvestmentRepository investmentRepository;
    @Autowired
    private UserService userService;
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

    // 투자 정보 업데이트
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
    public void deleteInvestment(Integer grantId) {
        if (investmentRepository.existsById(grantId)) {
            investmentRepository.deleteById(grantId);
        } else {
            throw new IllegalArgumentException("해당 투자 정보를 찾을 수 없습니다. ID: " + grantId);
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
        UserEntity user = userService.getUserById(userId);
        // InvestmentEntity 생성 및 저장
        InvestmentEntity investment = InvestmentEntity.builder()
                .userId(userId)
                .expectedIncome(latestIncomeEntity.getExpectedIncome())
                .status(InvestmentStatus.승인대기중) // 승인 대기중 상태
                .originalInvestValue(0L)
                .monthlyAllowance(0)
                .isActive(false)
                .maxInvestment((int)((totalPresentValue)*(0.2)/(1+rateofreturn))) // 우리 수익률 10%를 잡고
                .investValue(0L)
                .refundRate(0.0)
                .tempAllowance(0)
                .startDate( LocalDate.now()) // 투자 시작 날짜를 임시로 설정(투자 승인날에 업데이트 해줘야함)
                .endDate(user.getBirthdate().plusYears(65)) // 투자 종료 날짜를 사용자의 65세가 되는 날로 설정
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
        InvestmentEntity investment = investmentRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Investment not found for user ID: " + userId));

        double presentValue = expectedValueService.calculatePresentValue(userId);
        double refundRate = ((investment.getOriginalInvestValue() * 1.15) / presentValue * 100);
        refundRate = Math.round(refundRate * 1000) / 1000.0;

        investment.setRefundRate(refundRate);
        investmentRepository.save(investment);

        return refundRate;
    }
}
