package com.shinhan.peoch.benefit.entity;

import com.shinhan.peoch.card.entity.CardEntity;
import com.shinhan.peoch.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "myBenefits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyBenefitEntity extends BaseEntity {

    @EmbeddedId
    private MyBenefitId myBenefitId;

    @ManyToOne
    @MapsId("benefitId")
    @JoinColumn(name = "benefitId")
    BenefitEntity benefit;

    @ManyToOne
    @MapsId("cardId")
    @JoinColumn(name = "cardId")
    CardEntity card;

    private LocalDateTime date;

}
