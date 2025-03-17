package com.shinhan.peoch.UserProfileNormalization.perplexity;

import com.shinhan.entity.UserProfileEntity;
import com.shinhan.entity.NormUserProfilesEntity;
import com.shinhan.repository.UserProfileRepository;
import com.shinhan.repository.NormUserProfilesRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileNormalizationPerplexityService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private NormUserProfilesRepository normUserProfilesRepository;

    @Autowired
    private PerplexityApiRequest perplexityApiRequest;

    private static final int DEFAULT_SCORE = 50; // 기본값

    @Transactional
    public NormUserProfilesEntity normalizeAndSaveUserProfile(Integer userProfileId) throws JSONException {
        // 1. 사용자 프로필 조회
        UserProfileEntity userProfile = userProfileRepository.findById(userProfileId)
                .orElseThrow(() -> new RuntimeException("User profile not found with ID: " + userProfileId));

        System.out.println(userProfile.toString());

        // 2. 프로필 데이터를 JSON 형태로 변환
        JSONObject profileData = convertProfileToJson(userProfile);

        // 3. AI 모델에 한 번에 요청하여 모든 필드 정규화
        JSONObject normalizedScores = normalizeAllFields(profileData);

        // 4. 정규화된 점수로 엔티티 생성
        NormUserProfilesEntity normalizedProfile = NormUserProfilesEntity.builder()
                .university(getScoreFromJson(normalizedScores, "university"))
                .educationMajor(getScoreFromJson(normalizedScores, "education_major"))
                .certification(getScoreFromJson(normalizedScores, "certification"))
                .familyStatus(getScoreFromJson(normalizedScores, "family_status"))
                .assets(getScoreFromJson(normalizedScores, "assets"))
                .criminalRecord(getScoreFromJson(normalizedScores, "criminal_record"))
                .healthStatus(getScoreFromJson(normalizedScores, "health_status"))
                .gender(userProfile.getGender()) // 성별은 기존 값 차용
                .address(getScoreFromJson(normalizedScores, "address"))
                .mentalStatus(getScoreFromJson(normalizedScores, "mental_status"))
                .build();

        return normUserProfilesRepository.save(normalizedProfile);
    }

    // 프로필 데이터를 JSON 형태로 변환
    private JSONObject convertProfileToJson(UserProfileEntity profile) throws JSONException {
        JSONObject profileData = new JSONObject();

        // 주소 추가
        if (profile.getAddress() != null && !profile.getAddress().isEmpty()) {
            profileData.put("address", profile.getAddress());
        }

        // 자산 추가
        if (profile.getAssets() != null) {
            profileData.put("assets", profile.getAssets());
        }

        // 자격증 추가
        if (profile.getCertification() != null && !profile.getCertification().isEmpty()) {
            profileData.put("certification", new JSONObject(profile.getCertification()));
        }

        // 범죄 기록 추가
        profileData.put("criminal_record", profile.getCriminalRecord() != null && profile.getCriminalRecord() ? 1 : 0);

        // 대학 및 교육 전공 정보 추가
        if (profile.getUniversityInfo() != null && !profile.getUniversityInfo().isEmpty()) {
            JSONObject uniInfo = new JSONObject(profile.getUniversityInfo());

            // 대학 정보 추가
            JSONObject university = new JSONObject();
            if (uniInfo.has("name")) {
                university.put("name", uniInfo.getString("name"));
            }
            if (uniInfo.has("degree")) {
                university.put("degree", uniInfo.getString("degree"));
            }
            profileData.put("university", university);

            // 교육 전공 정보 추가
            JSONObject educationMajor = new JSONObject();
            if (uniInfo.has("degree")) {
                educationMajor.put("degree", uniInfo.getString("degree"));
            }
            profileData.put("education_major", educationMajor);
        }

        // 가족 상태 추가
        if (profile.getFamilyStatus() != null && !profile.getFamilyStatus().isEmpty()) {
            profileData.put("family_status", new JSONObject(profile.getFamilyStatus()));
        }

        // 건강 상태 추가
        if (profile.getHealthStatus() != null) {
            profileData.put("health_status", profile.getHealthStatus());
        }

        // 정신 상태 추가
        if (profile.getMentalStatus() != null) {
            profileData.put("mental_status", profile.getMentalStatus());
        }

        return profileData;
    }


    // 모든 필드를 한 번에 정규화하는 메서드
    private JSONObject normalizeAllFields(JSONObject profileData) throws JSONException {
        String prompt = "다음 사용자 프로필 데이터의 각 항목을 0에서 100 사이의 점수로 평가해주세요. " +
                "각 항목은 사회적, 경제적 가치와 경쟁력 측면에서 평가합니다. " +
                "결과는 JSON 형식으로 각 필드별 점수만 반환해주세요. 다음 형식으로 응답해주세요:\n" +
                "{\n" +
                "  \"address\": 숫자,\n" +
                "  \"assets\": 숫자,\n" +
                "  \"certification\": 숫자,\n" +
                "  \"criminal_record\": 숫자,\n" +
                "  \"education_major\": 숫자,\n" +
                "  \"family_status\": 숫자,\n" +
                "  \"health_status\": 숫자,\n" +
                "  \"mental_status\": 숫자,\n" +
                "  \"university\": 숫자\n" +
                "}\n\n" +
                "프로필 데이터:\n" + profileData.toString();

        JSONObject payload = new JSONObject();
        payload.put("model", "sonar");

        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "당신은 사용자 프로필 데이터를 통해서 소득수준을 평가하는 AI입니다. 각 항목을 0-100 사이의 점수로 평가하고, " +
                "JSON 형식으로 결과를 반환해야 합니다. 각 필드는 원본 필드명을 유지하고, 값은 숫자(정수)여야 합니다.");

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);

        JSONArray messages = new JSONArray();
        messages.put(systemMessage);
        messages.put(userMessage);

        payload.put("messages", messages);

        // response_format 부분 제거 또는 수정
        // Perplexity API가 현재 지원하는 형식으로 변경
        JSONObject responseFormat = new JSONObject();
        responseFormat.put("type", "text"); // "json_schema" 대신 "text" 사용
        payload.put("response_format", responseFormat);

        String response = perplexityApiRequest.sendRequestToApi(payload);

        try {
            // 응답에서 JSON 부분 추출 시도
            String jsonStr = extractJsonFromText(response);
            return new JSONObject(jsonStr);
        } catch (JSONException e) {
            System.err.println("Failed to parse API response: " + response);
            return new JSONObject(); // 빈 객체 반환
        }
    }

    // 텍스트에서 JSON 부분 추출
    private String extractJsonFromText(String text) {
        // JSON 시작과 끝 부분 찾기
        int startIdx = text.indexOf('{');
        int endIdx = text.lastIndexOf('}') + 1;

        if (startIdx >= 0 && endIdx > startIdx) {
            return text.substring(startIdx, endIdx);
        }

        // JSON을 찾지 못한 경우 빈 JSON 객체 반환
        return "{}";
    }

    // JSON에서 점수 추출
    private Integer getScoreFromJson(JSONObject scores, String fieldName) {
        if (scores.has(fieldName)) {
            try {
                return scores.getInt(fieldName);
            } catch (JSONException e) {
                return DEFAULT_SCORE;
            }
        }
        return DEFAULT_SCORE;
    }
}
