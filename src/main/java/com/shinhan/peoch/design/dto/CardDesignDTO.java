package com.shinhan.peoch.design.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.*;

@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "image")
public class CardDesignDTO {
 
    private int design_id;
    private String username;
    private String layout_id; 

    // 글자 색상: 0 (white), 1 (black)
   
    private int letterColor;
    private MultipartFile image;

}
