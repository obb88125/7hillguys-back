package com.shinhan.repository;

import com.shinhan.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    // 특정 사용자, 특정 기간 결제 내역 조회
    List<PaymentEntity> findByCard_User_UserIdAndDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    // 특정 사용자, 특정 상점, 특정 기간 결제 내역 조회
    List<PaymentEntity> findByCard_User_UserIdAndStore_StoreIdAndDateBetween(Long userId, Long storeId, LocalDateTime start, LocalDateTime end);
}
