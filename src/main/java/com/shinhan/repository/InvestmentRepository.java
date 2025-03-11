package com.shinhan.repository;

import com.shinhan.entity.InvestmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentEntity, Integer> {

    // 최신 updatedAt 기준으로 가장 최근 1개 엔티티 조회
    InvestmentEntity findFirstByUserIdOrderByUpdatedAtDesc(long userId);

    // 가장 오래된 createdAt 기준으로 가장 오래된 1개 엔티티 조회
    InvestmentEntity findFirstByUserIdOrderByCreatedAtAsc(long userId);
}
