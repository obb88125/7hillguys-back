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
public class MyBenefitEntity {

    @EmbeddedId
    private MyBenefitId myBenefitId;

    private Integer usedCount;

    private LocalDateTime date;

    private Boolean status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @MapsId("benefitId")
    @JoinColumn(name = "benefitId")
    BenefitEntity benefit;

    @ManyToOne
    @MapsId("cardId")
    @JoinColumn(name = "cardId")
    CardEntity card;

}
