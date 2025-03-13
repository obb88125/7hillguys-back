package com.shinhan.repository;

import com.shinhan.peoch.account.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BillRepository extends JpaRepository<BillEntity, Long> {
    Optional<BillEntity> findByUserId(Long userId);

}
