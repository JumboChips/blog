package com.jumbochips.poml_jpa.comment.service;

import com.jumbochips.poml_jpa.comment.domain.ProjectComment;
import com.jumbochips.poml_jpa.comment.dto.CommentRequestDto;
import com.jumbochips.poml_jpa.comment.dto.CommentResponseDto;
import com.jumbochips.poml_jpa.comment.repository.ProjectCommentRepository;
import com.jumbochips.poml_jpa.common.recaptcha.RecaptchaValidationService;
import com.jumbochips.poml_jpa.project.domain.Project;
import com.jumbochips.poml_jpa.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectCommentService implements CommentService{

    private final ProjectCommentRepository projectCommentRepository;
    private final ProjectRepository projectRepository;
    private final RecaptchaValidationService recaptchaValidationService;

    @Value("${recaptcha.site-key}")
    private String siteKey;

    @Override
    public List<CommentResponseDto> getAllComment(Long projectId) {
        return projectCommentRepository.findAllByProjectId(projectId).stream()
                .map(comment -> CommentResponseDto.builder()
                        .commentId(comment.getId())
                        .username(comment.getUsername())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .build()).toList();
    }

    @Override
    public CommentResponseDto createComment(CommentRequestDto dto) {
        boolean isHuman = recaptchaValidationService.verifyToken(
                dto.getRecaptchaToken(),
                dto.getRecaptchaAction(),
                siteKey
        );

        if (!isHuman) {
            throw new IllegalArgumentException("reCAPTCHA 인증에 실패했습니다. 로봇으로 판단됩니다.");
        }

        Project project = projectRepository.findById(dto.getBlogId())
                .orElseThrow(() -> new IllegalArgumentException("Blog not found"));

        ProjectComment comment = ProjectComment.builder()
                .project(project)
                .username(dto.getUsername())
                .pwd(dto.getPwd())
                .content(dto.getContent())
                .build();

        projectCommentRepository.save(comment);

        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .username(comment.getUsername())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    @Override
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto dto) {
        ProjectComment comment = projectCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        comment.updateContent(dto.getContent());
        projectCommentRepository.save(comment);

        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .username(comment.getUsername())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    @Override
    public void deleteComment(Long commentId) {
        projectCommentRepository.deleteById(commentId);
    }
}
