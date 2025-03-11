package com.shinhan.peoch.invest;

import com.shinhan.entity.NormUserProfilesEntity;
import com.shinhan.entity.UserProfileEntity;
import com.shinhan.peoch.UserProfileNormalization.UserProfileNormalizationService;
import com.shinhan.repository.UserProfileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class NormalizeTest2 {

    @Autowired
    private UserProfileNormalizationService normalizationService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Test
    public void testNormalizeAndSaveUserProfile() throws Exception {
        // Given: 데이터베이스에서 사용자 프로필 가져오기
        Integer testUserId = 2;
        Optional<UserProfileEntity> userProfileOpt = userProfileRepository.findById(testUserId);

        Assertions.assertTrue(userProfileOpt.isPresent(), "테스트를 위한 사용자 프로필이 데이터베이스에 존재하지 않습니다.");

        UserProfileEntity userProfile = userProfileOpt.get();
        System.out.println(userProfile.toString());
        NormUserProfilesEntity result = normalizationService.normalizeAndSaveUserProfile(userProfile.getUserProfileId());


        System.out.println("정규화된 사용자 프로필: " + result.toString());
    }
}
