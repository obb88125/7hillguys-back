package com.shinhan.peoch.invest.controller;

import com.shinhan.entity.UserProfileEntity;
import com.shinhan.peoch.invest.dto.UserProfileDTO;
import com.shinhan.peoch.invest.dto.UserProfileFileDTO;
import com.shinhan.peoch.invest.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping("/save")
    public ResponseEntity<UserProfileEntity> saveUserProfile(@RequestBody UserProfileDTO dto) {
        log.info("받은 데이터: {}", dto);
        UserProfileEntity savedProfile = userProfileService.saveUserProfile(dto);
        return ResponseEntity.ok(savedProfile);
    }

    @PostMapping("/file")
    public ResponseEntity<String> submitUserProfile(
            @RequestParam(value = "universityCertificate", required = false) MultipartFile universityCertificate,
            @RequestParam(value = "studentCardFile", required = false) MultipartFile studentCardFile,
            @RequestParam(value = "certificationFiles", required = false) MultipartFile[] certificationFiles,
            @RequestParam(value = "familyCertificate", required = false) MultipartFile familyCertificate,
            @RequestParam(value = "criminalRecordFile", required = false) MultipartFile criminalRecordFile
    ) {
        try {
            // 파일 저장 (컨트롤러에서 prefix 지정)
            String universityFilePath = userProfileService.saveFile(universityCertificate, "university");
            String studentCardFilePath = userProfileService.saveFile(studentCardFile, "student_card");
            String familyFilePath = userProfileService.saveFile(familyCertificate, "family");
            String criminalFilePath = userProfileService.saveFile(criminalRecordFile, "criminal");

            StringBuilder certPaths = new StringBuilder();
            if (certificationFiles != null) {
                for (MultipartFile certFile : certificationFiles) {
                    String certPath = userProfileService.saveFile(certFile, "certificate");
                    if (certPath != null) {
                        certPaths.append(certPath).append("\n");
                    }
                }
            }

            return ResponseEntity.ok("파일 업로드 성공\n"
                    + "대학 증명서: " + universityFilePath + "\n"
                    + "학생증: " + studentCardFilePath + "\n"
                    + "가족 증명서: " + familyFilePath + "\n"
                    + "범죄 기록: " + criminalFilePath + "\n"
                    + "자격증: " + certPaths.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("파일 업로드 실패: " + e.getMessage());
        }
    }
}