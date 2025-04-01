package com.jumbochips.poml_jpa.common.recaptcha;

import com.google.cloud.recaptchaenterprise.v1.RecaptchaEnterpriseServiceClient;
import com.google.recaptchaenterprise.v1.Assessment;
import com.google.recaptchaenterprise.v1.CreateAssessmentRequest;
import com.google.recaptchaenterprise.v1.Event;
import com.google.recaptchaenterprise.v1.ProjectName;
import org.springframework.stereotype.Service;

@Service
public class RecaptchaValidationService {
    private static final String PROJECT_ID = "jumbochips"; // Google Cloud 프로젝트 ID
    private static final float MIN_SCORE = 0.5f; // 허용 점수 기준

    public boolean verifyToken(String token, String expectedAction, String siteKey) {
        System.out.println("전달받은 token: " + token);
        System.out.println("전달받은 expectedAction: " + expectedAction);
        System.out.println("전달받은 siteKey: " + siteKey);

        try (RecaptchaEnterpriseServiceClient client = RecaptchaEnterpriseServiceClient.create()) {
            Event event = Event.newBuilder()
                    .setSiteKey(siteKey)
                    .setToken(token)
                    .build();

            CreateAssessmentRequest request = CreateAssessmentRequest.newBuilder()
                    .setParent(ProjectName.of(PROJECT_ID).toString())
                    .setAssessment(Assessment.newBuilder().setEvent(event).build())
                    .build();

            Assessment response = client.createAssessment(request);

            // 유효한 토큰인지 확인
            if (!response.getTokenProperties().getValid()) {
                System.out.println("Invalid token: " + response.getTokenProperties().getInvalidReason());
                return false;
            }

            // Action 값이 예상과 일치하는지 확인
            if (!expectedAction.equals(response.getTokenProperties().getAction())) {
                System.out.println("Unexpected action: " + response.getTokenProperties().getAction());
                return false;
            }

            // 점수 확인
            float score = response.getRiskAnalysis().getScore();
            System.out.println("reCAPTCHA Score: " + score);
            return score >= MIN_SCORE;

        } catch (Exception e) {
            System.err.println("reCAPTCHA 검증 중 예외 발생: " + e.getMessage());
            return false;
        }
    }
}
