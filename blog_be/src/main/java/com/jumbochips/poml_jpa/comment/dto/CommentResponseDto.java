package com.jumbochips.poml_jpa.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Builder
@Data
public class CommentResponseDto {
    private Long commentId;
    private String username;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Asia/Seoul") // +09:00 포함
    private OffsetDateTime createdAt;
}
