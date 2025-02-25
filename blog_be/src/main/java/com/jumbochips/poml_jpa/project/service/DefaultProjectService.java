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
    private final ProjectCategoryRepository categoryRepository;
    private final ProjectTagRepository projectTagRepository;
    private final ProjectPostTagRepository projectPostTagRepository;
    private final ProjectImageRepository projectImageRepository;

    @Override
    public List<ProjectResponseDto> getAllProject() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(project -> new ProjectResponseDto(
                        project.getId(),
                        project.getCategory().getId(),
                        project.getThumbnail(),
                        project.getTitle(),
                        project.getContent(),
                        project.getProjectPostTags().stream()
                                .map(projectTag -> projectTag.getTag().getId())
                                .toList()
                )).collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDto getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return ProjectResponseDto.builder()
                .categoryId(project.getCategory().getId())
                .thumbnail(project.getThumbnail())
                .title(project.getTitle())
                .content(project.getContent())
                .build();
    }

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) {
        User user = userRepository.findById(projectRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        ProjectCategory category = categoryRepository.findById(projectRequestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("category not found"));


        Project project = Project.builder()
                .user(user)
                .thumbnail(projectRequestDto.getThumbnail())
                .title(projectRequestDto.getTitle())
                .content(projectRequestDto.getContent())
                .category(category)
                .build();

        projectRepository.save(project);

        List<Long> tagIds = projectRequestDto.getTagIds();
        List<ProjectTag> tags = projectTagRepository.findAllById(tagIds);

        List<ProjectPostTag> projectTags = tags.stream()
                .map(tag -> ProjectPostTag.builder()
                        .project(project)
                        .tag(tag)
                        .build()
                ).toList();

        projectPostTagRepository.saveAll(projectTags);

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
                .categoryId(project.getCategory().getId())
                .thumbnail(project.getThumbnail())
                .title(project.getTitle())
                .content(project.getContent())
                .tagIds(tags.stream().map(ProjectTag::getId).collect(Collectors.toList()))
                .build();
    }

    @Override
    public ProjectResponseDto updateProject(Long projectId, ProjectRequestDto projectRequestDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        ProjectCategory category = categoryRepository.findById(projectRequestDto.getCategoryId())
                        .orElseThrow(() -> new IllegalArgumentException("category not found"));


        // 기존 ProjectTag 삭제
        projectPostTagRepository.deleteByProjectId(projectId);

        // 새로운 태그 추가
        List<Long> tagIds = projectRequestDto.getTagIds();
        List<ProjectTag> tags = projectTagRepository.findAllById(tagIds);

        System.out.println("🚀 projectRequestDto.getCategoryId(): " + projectRequestDto.getCategoryId());
        System.out.println("🚀 projectRequestDto.getTagIds(): " + projectRequestDto.getTagIds());
        System.out.println("🚀 projectRequestDto.getTitle(): " + projectRequestDto.getTitle());
        System.out.println("🚀 projectRequestDto.getContent(): " + projectRequestDto.getContent());


        List<ProjectPostTag> newProjectTags = tags.stream()
                .map(tag -> ProjectPostTag.builder()
                        .project(project)
                        .tag(tag)
                        .build())
                .collect(Collectors.toList());

        projectPostTagRepository.saveAll(newProjectTags);

        project.updateTitle(projectRequestDto.getTitle());
        project.updateContent(projectRequestDto.getContent());
        project.updateCategory(category);
        projectRepository.save(project);

        return ProjectResponseDto.builder()
                .projectId(project.getId())
                .categoryId(project.getCategory().getId())
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
