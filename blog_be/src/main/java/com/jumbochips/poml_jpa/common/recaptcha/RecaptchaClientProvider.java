package com.jumbochips.poml_jpa.common.recaptcha;

import com.google.cloud.recaptchaenterprise.v1.RecaptchaEnterpriseServiceClient;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RecaptchaClientProvider {
    private final RecaptchaEnterpriseServiceClient client;

    public RecaptchaClientProvider() throws IOException {
        this.client = RecaptchaEnterpriseServiceClient.create();
    }

    public RecaptchaEnterpriseServiceClient getClient() {
        return client;
    }
}
