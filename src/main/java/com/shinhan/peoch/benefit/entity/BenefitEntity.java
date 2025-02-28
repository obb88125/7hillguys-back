package com.shinhan.peoch.benefit.entity;

import com.shinhan.peoch.payment.entity.StoreEntity;
import com.shinhan.peoch.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "benefits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BenefitEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long benefitId;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private BenefitType type;

    private Float discount_rate;
    private Integer max_discount;
    private Integer min_payment;
    private Integer usage_limit;

    @ManyToOne
    @JoinColumn(name = "storeId")
    StoreEntity store;

}
