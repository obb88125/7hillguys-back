package com.shinhan.repository;

import com.shinhan.entity.ExpectedIncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpectedIncomeRepository extends JpaRepository<ExpectedIncomeEntity, Integer> {
    List<ExpectedIncomeEntity> findByUserProfileId(Integer userProfileId);
}
