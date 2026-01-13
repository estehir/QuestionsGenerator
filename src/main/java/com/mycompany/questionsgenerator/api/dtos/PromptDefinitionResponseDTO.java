package com.mycompany.questionsgenerator.api.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PromptDefinitionResponseDTO {

    private Long id;
    private String version;
    private String description;
    private String text;
    private Boolean active;
    private LocalDateTime createdAt;
}

