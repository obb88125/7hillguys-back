package com.shinhan.peoch.benefit.service;


import com.shinhan.entity.*;
import com.shinhan.peoch.benefit.dto.BenefitApplyDTO;
import com.shinhan.peoch.benefit.dto.BenefitResponseDTO;
import com.shinhan.repository.BenefitRepository;
import com.shinhan.repository.CardRepository;
import com.shinhan.repository.MyBenefitRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyBenefitService {

    private final MyBenefitRepository myBenefitRepository;
    private final BenefitRepository benefitRepository;
    private final CardRepository cardRepository;
    private final EntityManager entityManager;

    // 카드ID에 따라 혜택 데이터를 조회하는 예시 (실제 구현은 카드와 혜택의 관계에 따라 달라짐)
    @Transactional(readOnly = true)
    public BenefitResponseDTO getBenefitsByUserId(Long userId) {
        // 내 카드 정보를 사용자 ID로 조회 (CardEntity에 user 연관관계가 있다고 가정)
        CardEntity card = cardRepository.findByUser_UserId(userId);
         //       .orElseThrow(() -> new IllegalArgumentException("카드를 찾을 수 없습니다."));

        // 카드에 적용된 혜택 조회 (BenefitEntity에 card 관계가 있고, 메서드 이름은 findAppliedByCard_CardId 사용)
        List<MyBenefitEntity> appliedBenefits = myBenefitRepository.findAppliedByCard_CardId(card.getCardId());
        // 전체 사용 가능한 혜택 조회 (사용자 기준, 필요에 따라 사용자 ID로 조회하는 메서드)
        List<BenefitEntity> availableBenefits = benefitRepository.findAll();

        return new BenefitResponseDTO(card, appliedBenefits, availableBenefits);
    }
    public void deleteBenefit(Long benefitId, Long cardId) {
        // 수정: benefitId가 첫 번째, cardId가 두 번째
        MyBenefitId id = new MyBenefitId(benefitId, cardId);
        if (myBenefitRepository.existsById(id)) {
            myBenefitRepository.deleteById(id);
        } else {
            throw new RuntimeException("해당 카드에 적용된 혜택이 존재하지 않습니다.");
        }
    }

    // 결제 시 혜택 적용 (임시 추가된 혜택을 카드에 병합하는 로직)
    @Transactional
    public void applyBenefits(BenefitApplyDTO dto) {
        CardEntity cardProxy = entityManager.getReference(CardEntity.class, dto.getCardId());

        // 혜택 ID 리스트를 순회하며 프록시 객체와 함께 MyBenefitEntity 생성
        for (Long benefitId : dto.getBenefitIds()) {
            // 혜택 프록시 객체 생성 (DB에 조회하지 않음)
            BenefitEntity benefitProxy = entityManager.getReference(BenefitEntity.class, benefitId);

            MyBenefitId myBenefitId = new MyBenefitId(dto.getCardId(), benefitId);
            MyBenefitEntity myBenefitEntity = MyBenefitEntity.builder()
                    .myBenefitId(myBenefitId)
                    .usedCount(0)
                    .status(MyBenefitStatus.ACTIVE) // 예시 상태
                    .card(cardProxy)
                    .benefit(benefitProxy)
                    .build();
            myBenefitRepository.save(myBenefitEntity);
        }

        // benefitRepository.applyBenefits(dto.getCardId(), dto.getBenefitIds());
        // 실제 로직은 카드와 혜택 관계 테이블 업데이트 등으로 처리
    }
}

