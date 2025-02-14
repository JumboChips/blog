package com.jumbochips.poml_jpa.project.controller;

import com.jumbochips.poml_jpa.common.auth.dto.CustomUserDetails;
import com.jumbochips.poml_jpa.project.dto.ProjectRequestDto;
import com.jumbochips.poml_jpa.project.dto.ProjectResponseDto;
import com.jumbochips.poml_jpa.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
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

    @PutMapping("{projectdId}")
    public ResponseEntity<ProjectResponseDto> updateProject(
            @PathVariable Long projectdId,
            @RequestBody ProjectRequestDto projectRequestDto) {
        try {
            ProjectResponseDto project = projectService.updateProject(projectdId, projectRequestDto);
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
