package com.shinhan.repository;

import com.shinhan.entity.MyBenefitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyBenefitsRepository extends JpaRepository<MyBenefitsEntity, Integer> {
}
