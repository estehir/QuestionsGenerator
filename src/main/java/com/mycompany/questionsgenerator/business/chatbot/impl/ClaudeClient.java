package com.mycompany.questionsgenerator.business.chatbot.impl;

import com.mycompany.questionsgenerator.business.chatbot.ChatbotClient;
import com.mycompany.questionsgenerator.business.models.enums.ChatbotProvider;
import org.springframework.stereotype.Component;

@Component
public class ClaudeClient implements ChatbotClient {

    @Override
    public ChatbotProvider getProvider() {
        return ChatbotProvider.CLAUDE;
    }

    @Override
    public String generate(String prompt) {
        // Stub temporário
        return "[CLAUDE RESPONSE] Prompt recebido:\n" + prompt;
    }
}
