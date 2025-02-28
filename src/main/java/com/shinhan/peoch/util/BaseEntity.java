package com.shinhan.peoch.util;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate  // 최초 생성 시 자동 저장
    @Column(nullable = false, updatable = false)  // 생성일은 변경되지 않도록 설정
    private LocalDateTime created_at;

    @LastModifiedDate  // 수정될 때마다 자동 업데이트
    private LocalDateTime updated_at;
}
