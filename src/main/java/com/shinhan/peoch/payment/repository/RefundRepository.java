package com.shinhan.peoch.payment.repository;

import com.shinhan.peoch.payment.entity.RefundEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRepository extends JpaRepository<RefundEntity, Long> {
    // 특정 결제 건의 환불 내역 조회
    List<RefundEntity> findByPaymentId(Long paymentId);
}
