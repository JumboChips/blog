package com.jumbochips.poml_jpa.comment.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class CommentRequestDto {
    private String username;
    private String password;
    private String content;
    private String recaptchaToken;
}
