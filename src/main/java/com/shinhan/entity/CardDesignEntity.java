package com.shinhan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "card_design")
public class CardDesignEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer designId;

    @Column(nullable = false)
    private Integer cardId; // CardsEntity와의 외래 키 관계

    @Enumerated(EnumType.STRING)
    private LayoutType layoutId; // 레이아웃 타입 (ENUM)

    private String imageUrl;

    private Float stickerX;

    private Float stickerY;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
