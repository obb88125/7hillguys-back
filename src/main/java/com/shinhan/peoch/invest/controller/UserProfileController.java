package com.shinhan.peoch.invest.controller;

import com.shinhan.entity.UserProfileEntity;
import com.shinhan.peoch.invest.dto.UserProfileDTO;
import com.shinhan.peoch.invest.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/file")
//    public ResponseEntity<String> submitUserProfile(@ModelAttribute UserProfileDTO userProfileDTO) {
//        try {
//            // 로컬 서버 저장 방식
//            String universityFilePath = userProfileService.saveFile(userProfileDTO.getUniversityCertificate());
//            String familyFilePath = userProfileService.saveFile(userProfileDTO.getFamilyCertificate());
//
//            // S3 저장 방식 (사용할 경우)
//            // String universityFilePath = userProfileService.uploadFileToS3(userProfileDTO.getUniversityCertificate());
//            // String familyFilePath = userProfileService.uploadFileToS3(userProfileDTO.getFamilyCertificate());
//
//            return ResponseEntity.ok("파일 업로드 성공\n대학 증명서: " + universityFilePath + "\n가족 증명서: " + familyFilePath);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("파일 업로드 실패: " + e.getMessage());
//        }
//    }
}
