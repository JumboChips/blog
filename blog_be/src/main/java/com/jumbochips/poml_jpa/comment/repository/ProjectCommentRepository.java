package com.jumbochips.poml_jpa.comment.repository;

import com.jumbochips.poml_jpa.comment.domain.ProjectComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectCommentRepository extends JpaRepository<ProjectComment, Long> {
    public List<ProjectComment> findAllByProjectId(Long projectId);

}
