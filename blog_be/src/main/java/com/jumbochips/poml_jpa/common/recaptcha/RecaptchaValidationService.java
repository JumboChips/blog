package com.jumbochips.poml_jpa.common.recaptcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class RecaptchaValidationService {

    @Value("${recaptcha.url}")
    private String url;

    @Value("${recaptcha.secret}")
    private String secretKey;

    public boolean verifyRecaptcha(String token){
        try {
            // 파라미터 설정
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("secret", secretKey);
            params.add("response", token);

            // 요청 보내기
            RestTemplate restTemplate = new RestTemplate();
            RecaptchaResponse response = restTemplate.postForObject(url, params, RecaptchaResponse.class);

            return response != null && response.isSuccess();
        } catch (Exception e) {
            System.err.println("reCAPTCHA 검증 실패: " + e.getMessage());
            return false;
        }
    }
}
