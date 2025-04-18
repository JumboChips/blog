package com.jumbochips.poml_jpa.comment.service;

import com.jumbochips.poml_jpa.comment.dto.CommentRequestDto;
import com.jumbochips.poml_jpa.comment.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    List<CommentResponseDto> getAllComment(Long postId);
    CommentResponseDto createComment (Long postId, CommentRequestDto commentRequestDto);
    CommentResponseDto updateComment (Long commentId, CommentRequestDto commentRequestDto);
    void deleteComment (Long commentId, String password);
}
