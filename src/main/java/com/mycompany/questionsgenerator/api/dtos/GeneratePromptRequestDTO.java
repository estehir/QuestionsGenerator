package com.mycompany.questionsgenerator.api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GeneratePromptRequestDTO {

    private Long templateId;

    /**
     * Map:
     * key   -> variable code (ex: DIFFICULTY)
     * value -> valor informado pelo usuário
     */
    private Map<String, String> variables;
}
