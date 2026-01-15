package com.mycompany.questionsgenerator.business.services;

import com.mycompany.questionsgenerator.business.chatbot.ChatbotClient;
import com.mycompany.questionsgenerator.business.interfaces.IQuestionGenerationHistoryService;
import com.mycompany.questionsgenerator.business.models.QuestionGenerationHistory;
import com.mycompany.questionsgenerator.business.vo.PromptGenerationVO;
import com.mycompany.questionsgenerator.persistence.interfaces.IQuestionGenerationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionGenerationHistoryService implements IQuestionGenerationHistoryService {

    private final IQuestionGenerationHistoryRepository repository;

    public void recordGeneration(PromptGenerationVO prompt, ChatbotClient chatbot, String answer) {

        QuestionGenerationHistory history =
                QuestionGenerationHistory.builder()
                        .templateId(prompt.getTemplateId())
                        .templateVersionId(prompt.getTemplateVersionId())
                        .promptDefinitionId(prompt.getPromptDefinitionId())
                        .chatbotProviderCode(chatbot.getProvider().name())
                        .variablesSnapshot(prompt.getVariablesSnapshot())
                        .finalPrompt(prompt.getFinalPrompt())
                        .chatbotResponse(answer)
                        .build();

        repository.save(history);
    }

}

