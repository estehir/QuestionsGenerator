package com.mycompany.questionsgenerator.api.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GeneratePromptResponseDTO {

    private String prompt;
}
