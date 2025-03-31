package com.jumbochips.poml_jpa.comment.service;

import com.jumbochips.poml_jpa.blog.domain.Blog;
import com.jumbochips.poml_jpa.blog.repository.BlogRepository;
import com.jumbochips.poml_jpa.comment.domain.BlogComment;
import com.jumbochips.poml_jpa.comment.dto.CommentRequestDto;
import com.jumbochips.poml_jpa.comment.dto.CommentResponseDto;
import com.jumbochips.poml_jpa.comment.repository.BlogCommentRepository;
import com.jumbochips.poml_jpa.comment.repository.ProjectCommentRepository;
import com.jumbochips.poml_jpa.common.recaptcha.RecaptchaValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogCommentService implements CommentService{
    private final BlogCommentRepository blogCommentRepository;
    private final BlogRepository blogRepository;
    private final RecaptchaValidationService recaptchaValidationService;

    // siteKey 환경변수에서 주입
    @Value("${recaptcha.site-key}")
    private String siteKey;


    @Override
    public List<CommentResponseDto> getAllComment(Long blogId) {
        return blogCommentRepository.findAllByBlogId(blogId).stream()
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

        Blog blog = blogRepository.findById(dto.getBlogId())
                .orElseThrow(() -> new IllegalArgumentException("Blog not found"));

        BlogComment comment = BlogComment.builder()
                .blog(blog)
                .username(dto.getUsername())
                .pwd(dto.getPwd())
                .content(dto.getContent())
                .build();

        blogCommentRepository.save(comment);

        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .username(comment.getUsername())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    @Override
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto dto) {
        BlogComment comment = blogCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        comment.updateContent(dto.getContent());
        blogCommentRepository.save(comment);

        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .username(comment.getUsername())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    @Override
    public void deleteComment(Long commentId) {
        blogCommentRepository.deleteById(commentId);
    }
}
