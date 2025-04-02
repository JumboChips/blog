package com.jumbochips.poml_jpa.comment.dto;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String username;
    private String password;
    private String content;
    private String recaptchaToken;
}
