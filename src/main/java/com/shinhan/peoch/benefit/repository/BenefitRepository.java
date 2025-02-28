package com.shinhan.peoch.benefit.repository;

import com.shinhan.peoch.benefit.entity.BenefitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BenefitRepository extends JpaRepository<BenefitEntity, Long> {
    // 특정 가맹점에서 제공하는 혜택 목록 조회
    List<BenefitEntity> findByStoreId(Long storeId);

    // 최소 결제 금액 조건을 충족하는 혜택 조회
    List<BenefitEntity> findByMinPaymentLessThanEqual(int amount);
}
