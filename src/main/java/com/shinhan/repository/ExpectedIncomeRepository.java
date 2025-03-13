package com.shinhan.repository;

import com.shinhan.entity.ExpectedIncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpectedIncomeRepository extends JpaRepository<ExpectedIncomeEntity, Integer> {
    List<ExpectedIncomeEntity> findByUserProfileId(Integer userProfileId);
    // 최신 updatedAt 기준으로 가장 최근 1개 엔티티 조회
    Optional<ExpectedIncomeEntity> findFirstByUserProfileIdOrderByCreatedAtDesc(Integer userProfileId);
     // CreatedAt 기준으로 가장 과거 1개 엔티티 조회
     Optional<ExpectedIncomeEntity> findFirstByUserProfileIdOrderByCreatedAtAsc(Integer userProfileId);
}
