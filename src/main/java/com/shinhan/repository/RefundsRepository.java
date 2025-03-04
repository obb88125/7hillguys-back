package com.shinhan.repository;

import com.shinhan.entity.RefundsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundsRepository extends JpaRepository<RefundsEntity, Integer> {
}
