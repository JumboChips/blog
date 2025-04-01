package com.jumbochips.poml_jpa.comment.controller;

import com.jumbochips.poml_jpa.comment.dto.CommentRequestDto;
import com.jumbochips.poml_jpa.comment.dto.CommentResponseDto;
import com.jumbochips.poml_jpa.comment.service.BlogCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/comments/blog")
@RequiredArgsConstructor
public class BlogCommentController {
    private final BlogCommentService commentService;

    @GetMapping("{blogId}")
    public ResponseEntity<List<CommentResponseDto>> getBlogComment(
            @PathVariable Long blogId
    ) {
        try {
        List<CommentResponseDto> commentResponseDtos = commentService.getAllComment(blogId);
            return ResponseEntity.ok(commentResponseDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{blogId}")
    public ResponseEntity<?> createComment(
            @PathVariable Long blogId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        try {
            CommentResponseDto commentResponseDto = commentService.createComment(blogId, commentRequestDto);
            return ResponseEntity.ok(commentResponseDto);
        } catch (IllegalArgumentException e) {
            // JSON으로 메시지 내려주기
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "서버 오류가 발생했습니다."));
        }
    }

    @PutMapping("{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        try {
            CommentResponseDto commentResponseDto = commentService.updateComment(commentId, commentRequestDto);
            return ResponseEntity.ok(commentResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<CommentResponseDto> deleteComment(
            @PathVariable Long commentId
    ) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
