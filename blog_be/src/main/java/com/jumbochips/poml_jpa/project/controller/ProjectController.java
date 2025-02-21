package com.jumbochips.poml_jpa.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jumbochips.poml_jpa.common.auth.dto.CustomUserDetails;
import com.jumbochips.poml_jpa.project.dto.ProjectRequestDto;
import com.jumbochips.poml_jpa.project.dto.ProjectResponseDto;
import com.jumbochips.poml_jpa.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        try {
            List<ProjectResponseDto> projects = projectService.getAllProject();
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{projectId}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable Long projectId) {
        try {
            ProjectResponseDto project = projectService.getProject(projectId);
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(
            @RequestBody ProjectRequestDto projectRequestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            // userDetails가 null인지 확인
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
            // 인증된 사용자 정보에서 userId 추출
            Long userId = userDetails.getUserId();
            projectRequestDto.setUserId(userId);

            ProjectResponseDto project = projectService.createProject(projectRequestDto);
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{projectId}")
    public ResponseEntity<ProjectResponseDto> updateProject(
            @PathVariable Long projectId,
            @RequestBody ProjectRequestDto projectRequestDto) {
        try {
            ProjectResponseDto project = projectService.updateProject(projectId, projectRequestDto);
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{projectId}")
    public ResponseEntity<ProjectResponseDto> deleteProject(@PathVariable Long projectId) {
        try {
            projectService.deleteProject(projectId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
