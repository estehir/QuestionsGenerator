package com.mycompany.questionsgenerator.api.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class TemplateVersionResponseDTO {

    private Long templateId;
    private String templateName;

    private Long versionId;
    private Integer version;

    private String templateText;
    private LocalDateTime createdAt;

    private List<TemplateVariableResponseDTO> variables;
}

