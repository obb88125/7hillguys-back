package com.shinhan.repository;

import com.shinhan.entity.InvestmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentEntity, Integer> {
    Optional<InvestmentEntity> findByUserId(Integer grantId);
}
