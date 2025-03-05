package com.shinhan.peoch.invest.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserProfileDTO {
    private Integer userId;
    private String universityInfo;
    private String studentCard;
    private String certification;
    private String familyStatus;
    private Long assets;
    private Boolean criminalRecord;
    private Integer healthStatus;
    private Boolean gender;
    private String address;
    private Integer mentalStatus;
}
