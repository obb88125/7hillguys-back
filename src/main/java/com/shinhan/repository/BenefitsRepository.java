package com.shinhan.repository;

import com.shinhan.entity.BenefitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitsRepository extends JpaRepository<BenefitsEntity, Integer> {
}
