//package com.shinhan.peoch.invest;
//
//import com.shinhan.entity.InvestmentEntity;
//import com.shinhan.peoch.lifecycleincome.service.InvestmentService;
//import com.shinhan.repository.InvestmentRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import com.shinhan.entity.InvestmentStatus;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class InvestmentServiceTest {
//
//    @Autowired
//    private InvestmentRepository investmentRepository;
//
//    private InvestmentService investmentService;
//
//    @BeforeEach
//    void setUp() {
//        investmentService = new InvestmentService(investmentRepository);
//    }
//
//    @Test
//    @DisplayName("모든 투자 정보를 조회하는 테스트")
//    void findAllInvestmentsTest() {
////        // Given: 테스트용 데이터 생성 및 저장
////        InvestmentEntity investment1 = InvestmentEntity.builder()
////                .userId(1)
////                .expectedIncome("{\"year\": \"2025\", \"income\": \"5000\"}")
////                .startDate(LocalDate.of(2025, 1, 1))
////                .endDate(LocalDate.of(2025, 12, 31))
////                .status(InvestmentStatus.승인됨)
////                .originalInvestValue(100000L)
////                .isActive(true)
////                .build();
////
////        InvestmentEntity investment2 = InvestmentEntity.builder()
////                .userId(2)
////                .expectedIncome("{\"year\": \"2025\", \"income\": \"7000\"}")
////                .startDate(LocalDate.of(2025, 2, 1))
////                .endDate(LocalDate.of(2025, 11, 30))
////                .status(InvestmentStatus.거절됨)
////                .originalInvestValue(200000L)
////                .isActive(false)
////                .build();
////
////        investmentRepository.saveAll(Arrays.asList(investment1, investment2));
//
//        // When: findAllInvestments 메서드 호출
//        List<InvestmentEntity> investments = investmentService.findAllInvestments();
////
////        // Then: 결과 검증
////        assertThat(investments).hasSize(2); // 저장된 데이터가 2개인지 확인
//        assertThat(investments).extracting("userId").containsExactlyInAnyOrder(1, 2); // userId 확인
//    }
//}
