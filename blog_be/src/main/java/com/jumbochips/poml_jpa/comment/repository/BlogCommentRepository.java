package com.jumbochips.poml_jpa.comment.repository;

import com.jumbochips.poml_jpa.comment.domain.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {

    public List<BlogComment> findAllByBlogId(Long blogId);
}
