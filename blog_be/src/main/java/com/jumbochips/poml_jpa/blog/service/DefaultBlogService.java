package com.jumbochips.poml_jpa.blog.service;

import com.jumbochips.poml_jpa.blog.domain.*;
import com.jumbochips.poml_jpa.blog.dto.BlogResponseDto;
import com.jumbochips.poml_jpa.blog.repository.*;
import com.jumbochips.poml_jpa.blog.dto.BlogRequestDto;
import com.jumbochips.poml_jpa.user.domain.User;
import com.jumbochips.poml_jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultBlogService implements BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final BlogImageRepository blogImageRepository;

    @Override
    public List<BlogResponseDto> getAllBlog() {
        List<Blog> blogs = blogRepository.findAll();

        return blogs.stream()
                .map(blog -> new BlogResponseDto(
                        blog.getId(),
                        blog.getThumbnail(),
                        blog.getTitle(),
                        blog.getContent()
                )).collect(Collectors.toList());
    }

    @Override
    public BlogResponseDto getBlog(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        return BlogResponseDto.builder()
                .thumbnail(blog.getThumbnail())
                .title(blog.getTitle())
                .content(blog.getContent())
                .build();
    }

    @Override
    public BlogResponseDto createBlog(BlogRequestDto blogRequestDto) {
        User user = userRepository.findById(blogRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        Blog blog = Blog.builder()
                .user(user)
                .thumbnail(blogRequestDto.getThumbnail())
                .title(blogRequestDto.getTitle())
                .content(blogRequestDto.getContent())
                .build();

        blogRepository.save(blog);

        // 이미지 URL 저장

        List<BlogImage> images = blogRequestDto.getImageUrls().stream()
                .map(url -> BlogImage.builder()
                        .blog(blog)
                        .imageUrl(url)
                        .build())
                .collect(Collectors.toList());
        blogImageRepository.saveAll(images);


        return BlogResponseDto.builder()
                .blogId(blog.getId())
                .thumbnail(blog.getThumbnail())
                .title(blog.getTitle())
                .content(blog.getContent())
                .build();
    }

    @Override
    public BlogResponseDto updateBlog(Long blogId, BlogRequestDto blogRequestDto) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));


        blog.updateTitle(blogRequestDto.getTitle());
        blog.updateContent(blogRequestDto.getContent());
        blogRepository.save(blog);

        return BlogResponseDto.builder()
                .blogId(blog.getId())
                .thumbnail(blog.getThumbnail())
                .title(blog.getTitle())
                .content(blog.getContent())
                .build();
    }

    @Override
    public ResponseEntity<?> deleteBlog(Long blogId) {
        blogRepository.deleteById(blogId);
        return ResponseEntity.ok().build();
    }

}
