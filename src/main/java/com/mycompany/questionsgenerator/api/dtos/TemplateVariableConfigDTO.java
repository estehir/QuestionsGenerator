package com.mycompany.questionsgenerator.api.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TemplateVariableConfigDTO {

    /**
     * Código da variável (ex: TOPIC, DIFFICULTY)
     */
    private String code;

    /**
     * Se a variável é obrigatória no template
     */
    private Boolean required;

}
