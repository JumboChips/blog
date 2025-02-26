package com.jumbochips.poml_jpa.blog.controller;

import com.jumbochips.poml_jpa.blog.domain.BlogCategory;
import com.jumbochips.poml_jpa.blog.domain.BlogTag;
import com.jumbochips.poml_jpa.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blog/ct")
@RequiredArgsConstructor
public class BlogCategoryAndTagsController {

    private final BlogService blogService;

    @GetMapping("/categories")
    public ResponseEntity<List<BlogCategory>> getAllCategories() {
        try {
            List<BlogCategory> categories = blogService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tags")
    public ResponseEntity<List<BlogTag>> getAllTags() {
        try {
            List<BlogTag> tags = blogService.getAllTags();
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



}
