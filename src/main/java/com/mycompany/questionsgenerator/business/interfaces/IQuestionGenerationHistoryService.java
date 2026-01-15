package com.mycompany.questionsgenerator.business.interfaces;

import com.mycompany.questionsgenerator.business.chatbot.ChatbotClient;
import com.mycompany.questionsgenerator.business.vo.PromptGenerationVO;

public interface IQuestionGenerationHistoryService {

    void recordGeneration(PromptGenerationVO prompt, ChatbotClient chatbot, String answer);

}
