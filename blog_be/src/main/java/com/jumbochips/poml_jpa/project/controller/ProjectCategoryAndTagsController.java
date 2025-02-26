package com.jumbochips.poml_jpa.project.controller;

import com.jumbochips.poml_jpa.blog.domain.BlogCategory;
import com.jumbochips.poml_jpa.blog.domain.BlogTag;
import com.jumbochips.poml_jpa.project.domain.ProjectCategory;
import com.jumbochips.poml_jpa.project.domain.ProjectTag;
import com.jumbochips.poml_jpa.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project/meta")  // 새로운 네임스페이스 추가
@RequiredArgsConstructor
public class ProjectCategoryAndTagsController {
    private final ProjectService projectService;

    @GetMapping("categories")
    public ResponseEntity<List<ProjectCategory>> getAllCategories() {
        try {
            List<ProjectCategory> categories = projectService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("tags")
    public ResponseEntity<List<ProjectTag>> getAllTags() {
        try {
            List<ProjectTag> tags = projectService.getAllTags();
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
