package com.shinhan.peoch.benefit.repository;

import com.shinhan.peoch.benefit.entity.MyBenefitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyBenefitRepository extends JpaRepository<MyBenefitEntity, Long> {
    // 특정 카드의 혜택 목록 조회
    List<MyBenefitEntity> findByCardId(Long cardId);
}
