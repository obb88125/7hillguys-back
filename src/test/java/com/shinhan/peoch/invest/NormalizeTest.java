package com.shinhan.peoch.invest;

import com.shinhan.entity.NormUserProfilesEntity;
import com.shinhan.entity.UserProfileEntity;
import com.shinhan.peoch.UserProfileNormalization.UserProfileNormalizationService;
import com.shinhan.repository.UserProfileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class NormalizeTest {

    @Autowired
    private UserProfileNormalizationService normalizationService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Test
    public void testNormalizeAndSaveUserProfile() throws Exception {
        UserProfileEntity userProfile = UserProfileEntity.builder()
                .universityInfo("{\"name\":\"서울대학교\", \"degree\":\"컴퓨터공학과\"}")
                .certification("{\"certificate\":\"정보처리기사\"}")
                .familyStatus("{\"married\":false, \"children\":0}")
                .assets(10000000L)
                .criminalRecord(false)
                .healthStatus(85)
                .gender(true)
                .address("서울특별시 강남구")
                .mentalStatus(90)
                .build();


        // When: 정규화 서비스 호출
        NormUserProfilesEntity result = normalizationService.normalizeAndSaveUserProfile(userProfile.getUserProfileId());

        System.out.println("정규화된 사용자 프로필: " + result);
    }
}
