package com.mycompany.questionsgenerator.api.dtos;

import lombok.Data;

@Data
public class VariableOptionRequestDTO {

    private String code;
    private String label;
    private Integer numericValue;
    private Integer orderIndex;

}

