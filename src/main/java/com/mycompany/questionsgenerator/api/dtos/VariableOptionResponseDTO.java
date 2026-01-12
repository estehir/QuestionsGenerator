package com.mycompany.questionsgenerator.api.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VariableOptionResponseDTO {

    private String code;
    private String label;
    private Integer numericValue;
    private Integer orderIndex;
    private Boolean active;

}

