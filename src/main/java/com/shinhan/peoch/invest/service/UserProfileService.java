package com.shinhan.peoch.invest.service;

import com.shinhan.entity.UserProfileEntity;
import com.shinhan.peoch.invest.dto.UserProfileDTO;
import com.shinhan.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private static final String UPLOAD_DIR = "";
    //private final AmazonS3 amazonS3;

    //사용자 프로필 저장
    @Transactional
    public UserProfileEntity saveUserProfile(UserProfileDTO dto) {
        UserProfileEntity userProfileEntity = UserProfileEntity.builder()
                .userId(dto.getUserId())
                .universityInfo(dto.getUniversityInfo())
                .studentCard(dto.getStudentCard())
                .certification(dto.getCertification())
                .familyStatus(dto.getFamilyStatus())
                .assets(dto.getAssets())
                .criminalRecord(dto.getCriminalRecord())
                .healthStatus(dto.getHealthStatus())
                .gender(dto.getGender())
                .address(dto.getAddress())
                .mentalStatus(dto.getMentalStatus())
                .build();

        return userProfileRepository.save(userProfileEntity);
    }

    /** 로컬 서버에 파일 저장 **/
    public String saveFile(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            return null;
        }
        try {
            //파일명을 고유하게 변경
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String filePath = Paths.get(UPLOAD_DIR, fileName).toString();

            //파일저장
            File destFile = new File(filePath);
            file.transferTo(destFile);

            return filePath;    //DB에 저장할 파일 경로 반환
        } catch(IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }

    /** 아마존 S3 파일 저장 **/
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucketName;
//
//    public UserProfileService(AmazonS3 amazonS3) {
//        this.amazonS3 = amazonS3;
//    }
//
//    public String uploadFileToS3(MultipartFile file) {
//        if (file == null || file.isEmpty()) {
//            return null;
//        }
//        try {
//            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null));
//
//            return amazonS3.getUrl(bucketName, fileName).toString(); // S3 URL 반환
//        } catch (IOException e) {
//            throw new RuntimeException("S3 파일 업로드 실패", e);
//        }
//    }
}