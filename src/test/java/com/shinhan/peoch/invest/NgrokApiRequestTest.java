package com.shinhan.peoch.invest;

import com.shinhan.PeochApplication;
import com.shinhan.peoch.lifecycleincome.service.NgrokApiRequest;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = PeochApplication.class)
public class NgrokApiRequestTest {

    @Autowired
    private NgrokApiRequest ngrokApiRequest;

    @Test
    public void testSendRequestToApi() throws JSONException {
        // Given
        String model = "fixed-response";
        String prompt = """
                다음 대학교들의 생애 소득 잠재력을 0에서 100 사이로 평가하고, 미래 소득 순위를 매겨줘.
                평가는 다음 기준에 따라 이루어집니다:
                1. 졸업생의 평균 초봉
                2. 중간 경력 연봉
                3. 졸업 후 30년간 누적 소득
                4. 취업률 및 산업 연계성
                5. 연구 성과 및 국제적 명성

                숫자만 답변하고, 순위는 다음 형식으로 작성해줘:
                1. 대학 이름: 점수
                2. 대학 이름: 점수
                ...

                경상대학교, 카이스트, 연세대학교, 고려대학교, 부산대학교
                """;

        // When
        String response = ngrokApiRequest.sendRequestToApi(model, prompt);

        // Then
        System.out.println("API Response:\n" + response);
        // Assert that the response is not null or empty (you can add more specific assertions based on expected output)
        assert !response.isEmpty();
    }

}
