package com.jumbochips.poml_jpa.comment.service;

import com.jumbochips.poml_jpa.comment.domain.ProjectComment;
import com.jumbochips.poml_jpa.comment.dto.CommentRequestDto;
import com.jumbochips.poml_jpa.comment.dto.CommentResponseDto;
import com.jumbochips.poml_jpa.comment.repository.ProjectCommentRepository;
import com.jumbochips.poml_jpa.common.recaptcha.RecaptchaValidationService;
import com.jumbochips.poml_jpa.project.domain.Project;
import com.jumbochips.poml_jpa.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProjectCommentService implements CommentService{

    private final ProjectCommentRepository projectCommentRepository;
    private final ProjectRepository projectRepository;
    private final RecaptchaValidationService recaptchaValidationService;
    private final BCryptPasswordEncoder passwordEncoder;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h:mm").withZone(ZoneId.of("Asia/Seoul"));

    @Override
    public List<CommentResponseDto> getAllComment(Long projectId) {
        return projectCommentRepository.findAllByProjectId(projectId).stream()
                .map(comment -> CommentResponseDto.builder()
                        .commentId(comment.getId())
                        .username(comment.getUsername())
                        .content(comment.getContent())
                        .createdAt(formatter.format(comment.getCreatedAt()))
                        .build()).toList();
    }

    @Override
    public CommentResponseDto createComment(Long projectId, CommentRequestDto dto) {
        boolean isHuman = recaptchaValidationService.verifyRecaptcha(dto.getRecaptchaToken());

        if (!isHuman) {
            throw new IllegalArgumentException("reCAPTCHA 인증에 실패했습니다. 로봇으로 판단됩니다.");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Blog not found"));

        String encodedPwd = passwordEncoder.encode(dto.getPassword());

        ProjectComment comment = ProjectComment.builder()
                .project(project)
                .username(dto.getUsername())
                .pwd(encodedPwd)
                .content(dto.getContent())
                .build();

        projectCommentRepository.save(comment);

        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .username(comment.getUsername())
                .content(comment.getContent())
                .createdAt(formatter.format(comment.getCreatedAt()))
                .build();
    }

    @Override
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto dto) {
        ProjectComment comment = projectCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!passwordEncoder.matches(dto.getPassword(), comment.getPwd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        comment.updateContent(dto.getContent());
        projectCommentRepository.save(comment);

        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .username(comment.getUsername())
                .content(comment.getContent())
                .createdAt(formatter.format(comment.getCreatedAt()))
                .build();
    }

    @Override
    public void deleteComment(Long commentId, String password) {
        ProjectComment comment = projectCommentRepository.findById(commentId)
                        .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        if (!passwordEncoder.matches(password, comment.getPwd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        projectCommentRepository.deleteById(commentId);
    }
}
