package com.mycompany.questionsgenerator.api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromptDefinitionRequestDTO {

    private String description;
    private String text;
    private Boolean active;
}

