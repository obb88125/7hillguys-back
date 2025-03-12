package com.shinhan.peoch.card.service;
import com.shinhan.entity.CardEntity;
import com.shinhan.entity.CardStatus;
import com.shinhan.peoch.auth.entity.UserEntity;
import com.shinhan.peoch.card.dto.CardRequestDTO;
import com.shinhan.repository.CardRepository;
import com.shinhan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardApplicationService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createCard(CardRequestDTO cardDTO) {
        // (1) 카드번호 중복 체크 후 생성
        String cardNumber = generateRandomCardNumber();
//        String cardNumber;
//        do {
//
//        } while (cardRepository.existsByCardNumber(cardNumber));

        // (2) **사용자 정보 고정값으로 설정 (userId = 1L)**
        UserEntity user = userRepository.findById(16L)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // (3) 카드 엔티티 생성
        CardEntity newCard = CardEntity.builder()
                .cardNumber(cardNumber)
                .enName(cardDTO.getEnglishName())
                .password(cardDTO.getPin())
                .cvc(generateCVC())
                .issuedDate("25/03")
                .expirationDate("30/03")
                .status(CardStatus.ACTIVE)
                .monthlyAllowance(1000000)
                .tempAllowance(100000)
                .monthlySpent(0)
                .user(user) // 고정된 사용자 연결
                .build();

        // (4) DB 저장
        cardRepository.save(newCard);
    }

    private String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

    private String generateCVC() {
        Random random = new Random();
        return String.format("%03d", random.nextInt(1000));
    }
}
