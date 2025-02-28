package com.shinhan.peoch.card.entity;

import com.shinhan.peoch.auth.entity.UserEntity;
import com.shinhan.peoch.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(unique = true, length = 16)
    private String card_number;

    private String en_name;

    @Column(length = 4)
    private String password;

    @Column(length = 3)
    private String cvc;

    @Column(length = 5)
    private String issued_date;

    @Column(length = 5)
    private String expiration_date;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    private Integer monthly_allowance;
    private Integer temp_allowance;

    @OneToOne
    @JoinColumn(name = "userId")
    UserEntity user;
}
