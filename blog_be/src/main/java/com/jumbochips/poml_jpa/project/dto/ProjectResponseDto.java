package com.jumbochips.poml_jpa.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDto {
    private Long projectId;
    private String thumbnail;
    private String title;
    private String content;
}
