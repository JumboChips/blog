package com.jumbochips.poml_jpa.comment.service;

import com.jumbochips.poml_jpa.blog.domain.Blog;
import com.jumbochips.poml_jpa.blog.repository.BlogRepository;
import com.jumbochips.poml_jpa.comment.domain.BlogComment;
import com.jumbochips.poml_jpa.comment.dto.CommentRequestDto;
import com.jumbochips.poml_jpa.comment.dto.CommentResponseDto;
import com.jumbochips.poml_jpa.comment.repository.BlogCommentRepository;
import com.jumbochips.poml_jpa.common.recaptcha.RecaptchaValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogCommentService implements CommentService{
    private final BlogCommentRepository blogCommentRepository;
    private final BlogRepository blogRepository;
    private final RecaptchaValidationService recaptchaValidationService;
    private final BCryptPasswordEncoder passwordEncoder;

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
    public CommentResponseDto createComment(Long blogId, CommentRequestDto dto) {

        boolean isHuman = recaptchaValidationService.verifyRecaptcha(dto.getRecaptchaToken());
        if (!isHuman) {
            throw new IllegalArgumentException("reCAPTCHA 인증에 실패했습니다. 로봇으로 판단됩니다.");
        }

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new IllegalArgumentException("Blog not found"));

        String encodedPwd = passwordEncoder.encode(dto.getPassword());

        BlogComment comment = BlogComment.builder()
                .blog(blog)
                .username(dto.getUsername())
                .pwd(encodedPwd)
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

        if (!passwordEncoder.matches(dto.getPassword(), comment.getPwd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

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
    public void deleteComment(Long commentId, String password) {
        BlogComment comment = blogCommentRepository.findById(commentId)
                        .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!passwordEncoder.matches(password, comment.getPwd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        blogCommentRepository.deleteById(commentId);
    }
}
