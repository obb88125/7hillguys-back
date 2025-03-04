package com.shinhan.repository;

import com.shinhan.entity.StoresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoresRepository extends JpaRepository<StoresEntity, Integer> {
}
