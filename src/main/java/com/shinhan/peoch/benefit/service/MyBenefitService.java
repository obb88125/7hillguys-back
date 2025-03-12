package com.shinhan.peoch.benefit.service;


import com.shinhan.entity.BenefitEntity;
import com.shinhan.entity.CardEntity;
import com.shinhan.entity.MyBenefitEntity;
import com.shinhan.peoch.benefit.dto.BenefitApplyDTO;
import com.shinhan.peoch.benefit.dto.BenefitResponseDTO;
import com.shinhan.repository.BenefitRepository;
import com.shinhan.repository.CardRepository;
import com.shinhan.repository.MyBenefitRepository;
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

    // 카드ID에 따라 혜택 데이터를 조회하는 예시 (실제 구현은 카드와 혜택의 관계에 따라 달라짐)
    @Transactional(readOnly = true)
    public BenefitResponseDTO getBenefitsByUserId(Long userId) {
        // 내 카드 정보를 사용자 ID로 조회 (CardEntity에 user 연관관계가 있다고 가정)
        CardEntity card = cardRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("카드를 찾을 수 없습니다."));

        // 카드에 적용된 혜택 조회 (BenefitEntity에 card 관계가 있고, 메서드 이름은 findAppliedByCard_CardId 사용)
        List<MyBenefitEntity> appliedBenefits = myBenefitRepository.findAppliedByCard_CardId(card.getCardId());
        // 전체 사용 가능한 혜택 조회 (사용자 기준, 필요에 따라 사용자 ID로 조회하는 메서드)
        List<BenefitEntity> availableBenefits = benefitRepository.findAll();

        return new BenefitResponseDTO(card, appliedBenefits, availableBenefits);
    }
    // 적용된 혜택 삭제
    @Transactional
    public void deleteBenefit(Long benefitId) {
        // benefitRepository.deleteById(benefitId) 등 실제 삭제 로직 실행
        benefitRepository.deleteById(benefitId);
    }

    // 결제 시 혜택 적용 (임시 추가된 혜택을 카드에 병합하는 로직)
    @Transactional
    public void applyBenefits(BenefitApplyDTO dto) {
        // benefitRepository.applyBenefits(dto.getCardId(), dto.getBenefitIds());
        // 실제 로직은 카드와 혜택 관계 테이블 업데이트 등으로 처리
    }
}

