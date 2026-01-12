package com.mycompany.questionsgenerator.api.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VariableResponseDTO {

    private Long id;
    private String code;
    private String description;
    private String placeholder;
    private String valueType;

    private List<VariableOptionResponseDTO> options;
}

