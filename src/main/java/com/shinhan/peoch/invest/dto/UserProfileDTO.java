package com.shinhan.peoch.invest.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserProfileDTO {
    private Integer userId;
    private UniversityInfoDTO universityInfo;
    private studentCardDTO studentCard;
    private List<String> certification;
    private FamilyStatusDTO familyStatus;
    private Long assets;
    private Boolean criminalRecord;
    private Integer healthStatus;
    private Boolean gender;
    private String address;
    private Integer mentalStatus;
}

