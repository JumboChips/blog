package com.jumbochips.poml_jpa.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String username;
    private String pwd;
    private String content;
    private String recaptchaToken;
}
