package com.mycompany.questionsgenerator.business.services;

import com.mycompany.questionsgenerator.api.dtos.TemplateRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.TemplateResponseDTO;
import com.mycompany.questionsgenerator.business.interfaces.ITemplateService;
import com.mycompany.questionsgenerator.business.models.Template;
import com.mycompany.questionsgenerator.persistence.interfaces.ITemplateRepository;
import org.springframework.stereotype.Service;

@Service
public class TemplateService implements ITemplateService {

    private final ITemplateRepository templateRepository;

    public TemplateService(ITemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public TemplateResponseDTO createTemplate(TemplateRequestDTO request) {

        if (templateRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Template name already exists");
        }

        Template template = Template.builder()
                .name(request.getName())
                .templateText(request.getTemplateText())
                .build();

        Template savedTemplate = templateRepository.save(template);

        return TemplateResponseDTO.fromEntity(savedTemplate);
    }

    @Override
    public TemplateResponseDTO getTemplateById(Long id) {
        Template template = templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        return TemplateResponseDTO.fromEntity(template);
    }

}

