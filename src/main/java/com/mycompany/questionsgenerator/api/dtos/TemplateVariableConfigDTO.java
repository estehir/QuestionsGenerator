package com.mycompany.questionsgenerator.api.dtos;

import lombok.Data;

@Data
public class TemplateVariableConfigDTO {

    /**
     * Código da variável (ex: TOPIC, DIFFICULTY)
     */
    private String code;

    /**
     * Se a variável é obrigatória no template
     */
    private Boolean required = true;
}
