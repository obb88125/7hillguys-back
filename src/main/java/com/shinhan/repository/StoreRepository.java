package com.shinhan.repository;

import com.shinhan.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
        // 상점 ID로 상점엔티티 조회
        @Query("SELECT s FROM StoreEntity s WHERE s.storeId = :storeId")
        StoreEntity findStoreById(@Param("storeId") Long storeId);

}
