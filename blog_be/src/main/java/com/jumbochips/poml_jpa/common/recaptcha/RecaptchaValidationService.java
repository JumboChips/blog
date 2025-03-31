package com.jumbochips.poml_jpa.common.recaptcha;

import com.google.cloud.recaptchaenterprise.v1.RecaptchaEnterpriseServiceClient;
import com.google.recaptchaenterprise.v1.Assessment;
import com.google.recaptchaenterprise.v1.CreateAssessmentRequest;
import com.google.recaptchaenterprise.v1.Event;
import com.google.recaptchaenterprise.v1.ProjectName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecaptchaValidationService {

    private static final String PROJECT_ID = "jumbochips";
    private static final float MIN_SCORE = 0.5f;

    private final RecaptchaClientProvider recaptchaClientProvider;

    public boolean verifyToken(String token, String expectedAction, String siteKey) {
        try {
            RecaptchaEnterpriseServiceClient client = recaptchaClientProvider.getClient();

            Event event = Event.newBuilder()
                    .setSiteKey(siteKey)
                    .setToken(token)
                    .build();

            CreateAssessmentRequest request = CreateAssessmentRequest.newBuilder()
                    .setParent(ProjectName.of(PROJECT_ID).toString())
                    .setAssessment(Assessment.newBuilder().setEvent(event).build())
                    .build();

            Assessment response = client.createAssessment(request);

            if (!response.getTokenProperties().getValid()) {
                System.out.println("[reCAPTCHA] 유효하지 않은 토큰: " + response.getTokenProperties().getInvalidReason());
                return false;
            }

            if (!expectedAction.equals(response.getTokenProperties().getAction())) {
                System.out.printf("[reCAPTCHA] 예상한 액션 '%s'과 일치하지 않음: %s%n",
                        expectedAction, response.getTokenProperties().getAction());
                return false;
            }

            float score = response.getRiskAnalysis().getScore();
            System.out.println("[reCAPTCHA] 점수: " + score);
            return score >= MIN_SCORE;

        } catch (Exception e) {
            System.err.println("[reCAPTCHA] 검증 중 예외 발생: " + e.getMessage());
            return false;
        }
    }
}
