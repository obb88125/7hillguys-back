package com.shinhan.peoch.user.Repository;
 
import com.shinhan.peoch.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
 

public interface UserSearchRepository extends JpaRepository<UserEntity, Long> {
 
 // 이름만 검색 (이름이 null이거나 공백인 경우 전체 조회 로직은 별도로 처리)
 List<UserEntity> findByNameContaining(String name);


}
