//package com.shinhan.peoch.lifecycleincome.entity;
//
//import com.shinhan.entity.InvestmentStatus;
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Getter @Setter @Builder
//@NoArgsConstructor @AllArgsConstructor
//@Entity
//@Table(name = "investment")
//public class InvestmentEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer grantId;
//
//    @Column(nullable = false)
//    private Integer userId;
//
//    @Column(nullable = false, columnDefinition = "JSON")
//    private String expectedIncome;
//
//    @Column(nullable = false)
//    private LocalDate startDate;
//
//    @Column(nullable = false)
//    private LocalDate endDate;
//
//    @Enumerated(EnumType.STRING)
//    private InvestmentStatus status;
//
//    private Long originalInvestValue;
//
//    private Integer monthlyAllowance;
//
//    private Boolean isActive;
//
//    private Integer refundRate;
//
//    private Integer maxInvestment;
//
//    @Column(columnDefinition = "JSON")
//    private String field;
//
//    private Long investValue;
//
//    private Integer tempAllowance;
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;
//}