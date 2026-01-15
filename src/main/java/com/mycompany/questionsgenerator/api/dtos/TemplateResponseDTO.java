package com.mycompany.questionsgenerator.api.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateResponseDTO {

    private Long id;
    private String name;
    private Integer latestVersion;

}
