package com.shinhan.peoch.card.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoDTO {
    private String name;
    private String phone;
    private String email;
    private String address;
}

