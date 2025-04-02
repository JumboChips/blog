package com.jumbochips.poml_jpa.comment.controller;

import com.jumbochips.poml_jpa.comment.dto.CommentRequestDto;
import com.jumbochips.poml_jpa.comment.dto.CommentResponseDto;
import com.jumbochips.poml_jpa.comment.service.ProjectCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments/project")
@RequiredArgsConstructor
public class ProjectCommentController {

    private final ProjectCommentService commentService;

    @GetMapping("{projectId}")
    public ResponseEntity<List<CommentResponseDto>> getProjectComment(
            @PathVariable Long projectId
    ) {
        try {
            List<CommentResponseDto> commentResponseDtos = commentService.getAllComment(projectId);
            return ResponseEntity.ok(commentResponseDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{projectId}")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long projectId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        try {
            CommentResponseDto commentResponseDto = commentService.createComment(projectId, commentRequestDto);
            return ResponseEntity.ok(commentResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
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
            @PathVariable Long commentId,
            @RequestHeader("x-password") String password
    ) {
        try {
            commentService.deleteComment(commentId, password);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
