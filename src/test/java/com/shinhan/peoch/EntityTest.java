package com.shinhan.peoch;

import com.shinhan.PeochApplication;
import com.shinhan.entity.*;
import com.shinhan.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EnableJpaRepositories(basePackages = "com.shinhan.repository")
@EntityScan(basePackages = "com.shinhan.entity")
@SpringBootTest(classes = PeochApplication.class)


public class EntityTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private RefundsRepository refundsRepository;



    @Autowired
    private PaymentsRepository paymentsRepository;

    @Test
    public void testUserEntity() {
        UserEntity user = UserEntity.builder()
                .email("testuser@example.com")
                .password("securepassword")
                .name("Test User")
                .birthdate(LocalDate.of(1990, 1, 1))
                .phone("010-1234-5678")
                .address("Seoul, Korea")
                .role(UserRole.USER)
                .build();

        userRepository.save(user);
        System.out.println("User saved: " + user);
    }

    @Test
    public void testInvestmentEntity() {
        InvestmentEntity investment = InvestmentEntity.builder()
                .userId(1)
                .expectedIncome("{\"year\":2025,\"amount\":1000000}")
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2030, 1, 1))
                .status(InvestmentStatus.승인대기중)
                .originalInvestValue(1000000L)
                .monthlyAllowance(50000)
                .isActive(true)
                .maxInvestment(2000000)
                .field("{\"category\":\"Tech\"}")
                .investValue(1500000L)
                .tempAllowance(30000)
                .build();

        investmentRepository.save(investment);
        System.out.println("Investment saved: " + investment);
    }

    @Test
    public void testUserProfileEntity() {
        UserProfileEntity profile = UserProfileEntity.builder()
                .userId(1)
                .universityInfo("{\"name\":\"Seoul University\"}")
                .certification("{\"certs\":[\"Java\",\"AWS\"]}")
                .familyStatus("{\"members\":4}")
                .assets(10000000L)
                .criminalRecord(false)
                .healthStatus(90)
                .gender(true) // true = male, false = female
                .address("Seoul, Korea")
                .mentalStatus(80)
                .build();

        userProfileRepository.save(profile);
        System.out.println("User Profile saved: " + profile);
    }

    @Test
    public void testCardsEntity() {
        CardsEntity card = CardsEntity.builder()
                .userId(1)
                .cardNumber("1234-5678-9012-3456")
                .enName("VISA")
                .password("1234")
                .cvc("123")
                .issuedDate("2025-01-01")
                .expirationDate("2030-01-01")
                .status(CardStatus.ACTIVE)
                .monthlyAllowance(2000000)
                .tempAllowance(500000)
                .build();

        cardsRepository.save(card);
        System.out.println("Card saved: " + card);
    }

    @Test
    public void testRefundsEntity() {
        RefundsEntity refund = RefundsEntity.builder()
                .paymentId(1)
                .amount(50000L)
                .date(LocalDateTime.now())
                .build();

        refundsRepository.save(refund);
        System.out.println("Refund saved: " + refund);
    }

    @Test
    public void testPaymentsEntity() {
        PaymentsEntity payment = PaymentsEntity.builder()
                .cardId(1)
                .storeId(1)
                .originalAmount(100000L)
                .discountAmount(10000L)
                .finalAmount(90000L)
                .date(LocalDateTime.now())
                .status(PaymentStatus.PAID)
                .installmentMonth(12)
                .installmentRound(1)
                .currency("KRW")
                .build();

        paymentsRepository.save(payment);
        System.out.println("Payment saved: " + payment);
    }
}
