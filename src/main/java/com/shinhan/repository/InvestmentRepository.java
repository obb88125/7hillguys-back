package com.shinhan.repository;

import com.shinhan.entity.InvestmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentEntity, Integer> {
    List<InvestmentEntity> findByUserId(Integer userId);

    // 최신 updatedAt 기준으로 가장 최근 1개 엔티티 조회
    InvestmentEntity findFirstByUserIdOrderByUpdatedAtDesc(Integer userId);
}
