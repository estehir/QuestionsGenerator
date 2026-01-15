package com.mycompany.questionsgenerator.business.services;

import com.mycompany.questionsgenerator.api.dtos.GeneratePromptRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.GenerateQuestionResponseDTO;
import com.mycompany.questionsgenerator.business.chatbot.ChatbotClient;
import com.mycompany.questionsgenerator.business.chatbot.ChatbotClientResolver;
import com.mycompany.questionsgenerator.business.interfaces.IPromptGenerationService;
import com.mycompany.questionsgenerator.business.interfaces.IQuestionGenerationHistoryService;
import com.mycompany.questionsgenerator.business.interfaces.IQuestionGenerationService;
import com.mycompany.questionsgenerator.business.vo.PromptGenerationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionGenerationService implements IQuestionGenerationService {

    private final IPromptGenerationService promptService;
    private final ChatbotClientResolver chatbotResolver;
    private final IQuestionGenerationHistoryService questionGenerationHistoryService;

    @Override
    public GenerateQuestionResponseDTO generateQuestion(
            GeneratePromptRequestDTO request
    ) {

        // 1️⃣ Gera o prompt
        PromptGenerationVO promptVO = promptService.generatePrompt(request);

        // 2️⃣ Resolve chatbot ativo
        ChatbotClient chatbot = chatbotResolver.resolveActiveClient();

        // 3️⃣ Envia prompt ao chatbot
        String chatbotAnswer = chatbot.generate(promptVO.getFinalPrompt());

        // Salva histórico
        questionGenerationHistoryService.recordGeneration(
                promptVO,
                chatbot,
                chatbotAnswer
        );

        // 4️⃣ Retorna resultado
        return GenerateQuestionResponseDTO.builder()
                .chatbotProvider(chatbot.getProvider().name())
                .prompt(promptVO.getFinalPrompt())
                .response(chatbotAnswer)
                .build();
    }
}
