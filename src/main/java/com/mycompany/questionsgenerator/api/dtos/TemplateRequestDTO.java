package com.mycompany.questionsgenerator.api.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TemplateRequestDTO {

    private String name;
    private String templateText;

    /**
     * Configuração das variáveis do template
     */
    private List<TemplateVariableConfigDTO> variables;
}
