package com.shinhan.peoch.payment.repository;

import com.shinhan.peoch.payment.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    // 특정 카테고리의 가맹점 목록 조회
    List<StoreEntity> findByCategory(String category);
}
