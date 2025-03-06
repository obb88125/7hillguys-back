package com.shinhan.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "card_design")
public class CardDesignEntity {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int design_id;
 
    @Column(nullable = true)
    private String username;
 
    @Column(nullable = true)
    private String layout_id; 

    // 글자 색상: 0 (white), 1 (black)
    @Column(nullable = true)
    private int letterColor;
 
    @Lob
    @Column(nullable = true)
    private byte[] image;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
