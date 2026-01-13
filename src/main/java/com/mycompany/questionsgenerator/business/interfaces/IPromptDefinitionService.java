package com.mycompany.questionsgenerator.business.interfaces;

import com.mycompany.questionsgenerator.api.dtos.PromptDefinitionRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.PromptDefinitionResponseDTO;

import java.util.List;

public interface IPromptDefinitionService {

    PromptDefinitionResponseDTO create(PromptDefinitionRequestDTO request);

    PromptDefinitionResponseDTO activate(Long id);

    PromptDefinitionResponseDTO getActive();

    List<PromptDefinitionResponseDTO> getAll();
}

