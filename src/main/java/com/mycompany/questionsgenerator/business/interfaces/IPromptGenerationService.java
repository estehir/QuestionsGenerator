package com.mycompany.questionsgenerator.business.interfaces;

import com.mycompany.questionsgenerator.api.dtos.GeneratePromptRequestDTO;

public interface IPromptGenerationService {

    String generatePrompt(GeneratePromptRequestDTO request);
}
