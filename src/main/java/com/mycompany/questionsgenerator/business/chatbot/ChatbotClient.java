package com.mycompany.questionsgenerator.business.chatbot;

import com.mycompany.questionsgenerator.business.models.enums.ChatbotProvider;

public interface ChatbotClient {

    ChatbotProvider getProvider();

    /**
     * Recebe um prompt já resolvido e retorna a resposta do chatbot.
     */
    String generate(String prompt);
}
