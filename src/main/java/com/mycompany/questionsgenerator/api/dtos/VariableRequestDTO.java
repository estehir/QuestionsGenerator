package com.mycompany.questionsgenerator.api.dtos;

import lombok.Data;

import java.util.List;

@Data
public class VariableRequestDTO {

    private String code;
    private String description;
    private String placeholder;
    private String valueType; // TEXT, NUMBER, ENUM

    private List<VariableOptionRequestDTO> options;
}

