package com.mycompany.questionsgenerator.api.dtos;

import com.mycompany.questionsgenerator.business.models.Template;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateResponseDTO {

    private Long id;
    private String name;
    private String templateText;

    public static TemplateResponseDTO fromEntity(Template template) {
       return TemplateResponseDTO.builder()
                .id(template.getId())
                .name(template.getName())
                .templateText(template.getTemplateText())
                .build();
    }

}
