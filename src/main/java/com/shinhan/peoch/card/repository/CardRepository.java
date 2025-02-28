package com.shinhan.peoch.card.repository;

import com.shinhan.peoch.card.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository <CardEntity, Long> {
    // 카드 번호로 카드 조회
    Optional<CardEntity> findByCardNumber(String cardNumber);

    // 특정 사용자의 모든 카드 조회
    List<CardEntity> findByUserId(Long userId);
}
