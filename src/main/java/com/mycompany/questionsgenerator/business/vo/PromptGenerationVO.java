package com.mycompany.questionsgenerator.business.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class PromptGenerationVO {

    private Long templateId;
    private Long templateVersionId;
    private Long promptDefinitionId;

    private Map<String, String> variablesSnapshot;

    private String finalPrompt;
}

