package com.shinhan.repository;

import com.shinhan.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    // 특정 사용자, 특정 기간 결제 내역 조회
    List<PaymentEntity> findByCard_User_UserIdAndDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    // 특정 사용자, 특정 상점, 특정 기간 결제 내역 조회
    List<PaymentEntity> findByCard_User_UserIdAndStore_StoreIdAndDateBetween(Long userId, Long storeId, LocalDateTime start, LocalDateTime end);

    // 해당 userId 사용자의 discountAmount 총합 long으로 리턴
    @Query("SELECT SUM(p.discountAmount) FROM PaymentEntity p WHERE p.card.user.userId = :userId")
    long sumDiscountAmountByUserId(@Param("userId") long userId);

    // 해당 userId 사용자의 finalAmount 총합 long으로 리턴
    @Query("SELECT SUM(p.finalAmount) FROM PaymentEntity p WHERE p.card.user.userId = :userId")
    long sumFinalAmountByUserId(@Param("userId") long userId);
    // 해당 userId 사용자의 모든 결제 내역 조회
    List<PaymentEntity> findByCard_User_UserId(Long userId);

}
