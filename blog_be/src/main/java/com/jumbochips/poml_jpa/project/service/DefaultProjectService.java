package com.jumbochips.poml_jpa.project.service;

import com.jumbochips.poml_jpa.project.domain.*;
import com.jumbochips.poml_jpa.project.dto.ProjectRequestDto;
import com.jumbochips.poml_jpa.project.dto.ProjectResponseDto;
import com.jumbochips.poml_jpa.project.repository.*;
import com.jumbochips.poml_jpa.user.domain.User;
import com.jumbochips.poml_jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultProjectService implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectImageRepository projectImageRepository;

    @Override
    public List<ProjectResponseDto> getAllProject() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(project -> new ProjectResponseDto(
                        project.getId(),
                        project.getThumbnail(),
                        project.getTitle(),
                        project.getContent()
                )).collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDto getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return ProjectResponseDto.builder()
                .thumbnail(project.getThumbnail())
                .title(project.getTitle())
                .content(project.getContent())
                .build();
    }

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) {
        User user = userRepository.findById(projectRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));


        Project project = Project.builder()
                .user(user)
                .thumbnail(projectRequestDto.getThumbnail())
                .title(projectRequestDto.getTitle())
                .content(projectRequestDto.getContent())
                .build();

        projectRepository.save(project);

        // 이미지 URL 저장
        List<ProjectImage> images = projectRequestDto.getImageUrls().stream()
                .map(url -> ProjectImage.builder()
                        .project(project)
                        .imageUrl(url)
                        .build())
                .collect(Collectors.toList());
        projectImageRepository.saveAll(images);



        return ProjectResponseDto.builder()
                .projectId(project.getId())
                .thumbnail(project.getThumbnail())
                .title(project.getTitle())
                .content(project.getContent())
                .build();
    }

    @Override
    public ProjectResponseDto updateProject(Long projectId, ProjectRequestDto projectRequestDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));


        project.updateTitle(projectRequestDto.getTitle());
        project.updateContent(projectRequestDto.getContent());
        project.updateThumbnail(projectRequestDto.getThumbnail());
        projectRepository.save(project);

        return ProjectResponseDto.builder()
                .projectId(project.getId())
                .thumbnail(project.getThumbnail())
                .title(project.getTitle())
                .content(project.getContent())
                .build();
    }

    @Override
    public ResponseEntity<?> deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
        return ResponseEntity.ok().build();
    }

}
