package com.mycompany.questionsgenerator.business.interfaces;

import com.mycompany.questionsgenerator.api.dtos.GeneratePromptRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.GenerateQuestionResponseDTO;

public interface IQuestionGenerationService {

    GenerateQuestionResponseDTO generateQuestion(
            GeneratePromptRequestDTO request
    );
}
