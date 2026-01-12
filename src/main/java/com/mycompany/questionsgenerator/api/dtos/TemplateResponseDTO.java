package com.mycompany.questionsgenerator.api.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class TemplateResponseDTO {

    private Long id;
    private String name;
    private String templateText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<TemplateVariableResponseDTO> variables;
}
