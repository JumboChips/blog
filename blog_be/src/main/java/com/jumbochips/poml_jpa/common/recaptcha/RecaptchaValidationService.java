package com.jumbochips.poml_jpa.common.recaptcha;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.recaptchaenterprise.v1.RecaptchaEnterpriseServiceClient;
import com.google.cloud.recaptchaenterprise.v1.RecaptchaEnterpriseServiceSettings;
import com.google.recaptchaenterprise.v1.Assessment;
import com.google.recaptchaenterprise.v1.CreateAssessmentRequest;
import com.google.recaptchaenterprise.v1.Event;
import com.google.recaptchaenterprise.v1.ProjectName;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;

@Service
public class RecaptchaValidationService {
    private static final String PROJECT_ID = "jumbochips"; // Google Cloud 프로젝트 ID
    private static final float MIN_SCORE = 0.5f; // 허용 점수 기준
    private static final String CREDENTIALS_PATH = "/app/credentials/jumbochips-5d6505b07e33.json"; // 컨테이너 내부 경로

    public boolean verifyToken(String token, String expectedAction, String siteKey) {

        try {
            // 명시적으로 서비스 계정 키 경로를 지정해서 인증 객체 생성
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(CREDENTIALS_PATH));
            RecaptchaEnterpriseServiceSettings settings = RecaptchaEnterpriseServiceSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            try (RecaptchaEnterpriseServiceClient client = RecaptchaEnterpriseServiceClient.create(settings)) {
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
            }

        } catch (Exception e) {
            System.err.println("reCAPTCHA 검증 중 예외 발생: " + e.getMessage());
            return false;
        }
    }
}
