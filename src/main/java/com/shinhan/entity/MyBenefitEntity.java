package com.shinhan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "myBenefits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"benefit", "card"})
public class MyBenefitEntity {

    @EmbeddedId
    private MyBenefitId myBenefitId;

    private Integer usedCount;

    @Enumerated(EnumType.STRING)
    private MyBenefitStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @MapsId("benefitId")
    @JoinColumn(name = "benefit_id")
    BenefitEntity benefit;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @MapsId("cardId")
    @JoinColumn(name = "card_id")
    CardEntity card;

}
