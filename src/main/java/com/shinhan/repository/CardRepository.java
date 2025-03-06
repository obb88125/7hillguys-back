package com.shinhan.repository;

import com.shinhan.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository <CardEntity, Long> {
    // 특정 사용자의 카드 조회
    Optional<CardEntity> findByUser_UserId(Long userId);

    // 특정 사용자의 월 사용량 조회
    Integer findMonthlySpentByUser_UserId(Long userId);

    // 특정 사용자의 월 한도 조회
    Integer findMonthlyAllowanceByUser_UserId(Long userId);
}
