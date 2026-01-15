package com.mycompany.questionsgenerator.business.interfaces;

import com.mycompany.questionsgenerator.api.dtos.TemplateRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.TemplateResponseDTO;
import com.mycompany.questionsgenerator.api.dtos.TemplateVersionResponseDTO;

public interface ITemplateService {

    TemplateResponseDTO getTemplateById(Long id);

    TemplateVersionResponseDTO createTemplateVersion(TemplateRequestDTO request);

    TemplateVersionResponseDTO getLatestTemplateVersion(Long templateId);

    TemplateVersionResponseDTO getTemplateVersion(Long templateId, Integer version);

}
