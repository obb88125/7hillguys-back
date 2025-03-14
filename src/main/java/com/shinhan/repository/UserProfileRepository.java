package com.shinhan.repository;

import com.shinhan.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Integer> {
    Optional<UserProfileEntity> findByUserId(Integer userId);
    //제일 최신 profile
    Optional<UserProfileEntity> findFirstByUserIdOrderByUpdatedAtDesc(Integer userId);
    //제일 과거 profile
    Optional<UserProfileEntity> findFirstByUserIdOrderByUpdatedAtAsc(Integer userId);
    Optional<UserProfileEntity> findByUserProfileId(Integer userProfileId);

    List<UserProfileEntity> findByUserIdInAndGender(List<Long> userIds, Boolean gender);
}
