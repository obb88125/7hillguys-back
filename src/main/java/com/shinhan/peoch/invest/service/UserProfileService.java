package com.shinhan.peoch.invest.service;

import com.shinhan.entity.UserProfileEntity;
import com.shinhan.peoch.invest.dto.UserProfileDTO;
import com.shinhan.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

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
}
