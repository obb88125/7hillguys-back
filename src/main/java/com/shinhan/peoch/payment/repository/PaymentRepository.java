package com.shinhan.peoch.payment.repository;

import com.shinhan.peoch.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    // 특정 카드의 결제 내역 조회
    List<PaymentEntity> findByCardId(Long cardId);

    // 특정 기간 동안의 결제 내역 조회
    List<PaymentEntity> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // 결제 상태로 검색 (PAID, PENDING, REFUNDED)
    List<PaymentEntity> findByStatus(String status);
}
