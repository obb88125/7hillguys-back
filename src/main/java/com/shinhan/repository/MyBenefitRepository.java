package com.shinhan.repository;

import com.shinhan.entity.MyBenefitEntity;
import com.shinhan.entity.MyBenefitId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyBenefitRepository extends JpaRepository<MyBenefitEntity, MyBenefitId> {
    // 사용자가 사용 중인 혜택 ID 목록 조회
    @Query("select mb.benefit.benefitId from MyBenefitEntity mb where mb.card.user.userId = :userId")
    List<Long> findBenefitIdsByUserId(@Param("userId") Long userId);
}
