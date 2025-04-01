package com.jumbochips.poml_jpa.common.recaptcha;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecaptchaResponse {
    private boolean success;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    private String hostname;

    @JsonProperty("error-codes")
    private String[] errorCodes;
}
