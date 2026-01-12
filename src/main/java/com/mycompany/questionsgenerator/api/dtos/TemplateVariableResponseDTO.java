package com.mycompany.questionsgenerator.api.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateVariableResponseDTO {

    private String code;
    private String description;
    private Boolean required;
    private String valueType;
}
