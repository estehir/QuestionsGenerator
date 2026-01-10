package com.mycompany.questionsgenerator.business.interfaces;

import com.mycompany.questionsgenerator.api.dtos.TemplateRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.TemplateResponseDTO;

public interface ITemplateService {

    TemplateResponseDTO createTemplate(TemplateRequestDTO request);

    TemplateResponseDTO getTemplateById(Long id);

}