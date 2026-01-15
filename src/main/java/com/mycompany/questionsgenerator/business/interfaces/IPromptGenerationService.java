package com.mycompany.questionsgenerator.business.interfaces;

import com.mycompany.questionsgenerator.api.dtos.GeneratePromptRequestDTO;
import com.mycompany.questionsgenerator.business.vo.PromptGenerationVO;

public interface IPromptGenerationService {

    PromptGenerationVO generatePrompt(GeneratePromptRequestDTO request);
}
