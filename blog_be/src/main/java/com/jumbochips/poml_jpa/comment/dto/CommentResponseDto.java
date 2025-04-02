package com.jumbochips.poml_jpa.comment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CommentResponseDto {
    private Long commentId;
    private String username;
    private String content;
    private LocalDateTime createdAt;
}
