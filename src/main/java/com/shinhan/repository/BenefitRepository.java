package com.shinhan.repository;

import com.shinhan.entity.BenefitEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenefitRepository extends JpaRepository<BenefitEntity, Long> {
    @NonNull
    List<BenefitEntity> findAll();
}
