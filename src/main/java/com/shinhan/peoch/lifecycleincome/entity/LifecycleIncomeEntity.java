//package com.shinhan.peoch.lifecycleincome.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@ToString
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//
//@Entity
//@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
//public class LifecycleIncomeEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;
//
//    @Column(nullable = false, length = 255, unique = true)
//    private String email;
//
//    @Column(nullable = false, length = 255)
//    private String password;
//
//    @Column(nullable = false, length = 100)
//    private String name;
//
//    @Column(nullable = true)
//    private LocalDate birthdate;
//
//    @Column(nullable = true, length = 20)
//    private String phone;
//
//    @Column(nullable = true, length = 255)
//    private String address;
//
//
//    @CreationTimestamp
//    @Column(updatable = false)
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;
//}
