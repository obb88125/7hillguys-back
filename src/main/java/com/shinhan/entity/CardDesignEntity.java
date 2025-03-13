package com.shinhan.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private Long id;

    @Column(name = "layout_id")
    private Integer layoutId;

    
    @Column(name = "username")
    private String username;
     
    @Column(name = "letter_color")
    private int letterColor;
    
    @Column(name = "bg_image_url")
    private String bgImageUrl;
 
    @Column(name = "card_back_color")
    private String cardBackColor;
 
    @Column(name = "logo_grayscale")
    private boolean logoGrayscale;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private CardEntity card;
}
