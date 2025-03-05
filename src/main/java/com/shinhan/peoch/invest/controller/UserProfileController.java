package com.shinhan.peoch.invest.controller;

import com.shinhan.entity.UserProfileEntity;
import com.shinhan.peoch.invest.dto.UserProfileDTO;
import com.shinhan.peoch.invest.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping("/save")
    public ResponseEntity<UserProfileEntity> saveUserProfile(@RequestBody UserProfileDTO dto) {
        UserProfileEntity savedProfile = userProfileService.saveUserProfile(dto);
        return ResponseEntity.ok(savedProfile);
    }
}
