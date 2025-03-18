package com.shinhan.repository;

import com.shinhan.entity.ExpectedIncomeEntity;
import com.shinhan.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpectedIncomeRepository extends JpaRepository<ExpectedIncomeEntity, Integer> {
    // 직접 JPQL 쿼리 사용
    @Query("SELECT e FROM ExpectedIncomeEntity e WHERE e.userProfile.userProfileId = :userProfileId")
    List<ExpectedIncomeEntity> findByUserProfileId(@Param("userProfileId") Integer userProfileId);
    List<ExpectedIncomeEntity> findByUserProfile_UserProfileId(UserProfileEntity userProfile);
    // 최신 updatedAt 기준으로 가장 최근 1개 엔티티 조회
    Optional<ExpectedIncomeEntity> findFirstByUserProfileOrderByCreatedAtDesc(UserProfileEntity userProfile);

    // CreatedAt 기준으로 가장 과거 1개 엔티티 조회
     Optional<ExpectedIncomeEntity> findFirstByUserProfileOrderByCreatedAtAsc(UserProfileEntity userProfile);
    List<ExpectedIncomeEntity> findByUserProfileUserProfileId(Integer userProfileId);

}


