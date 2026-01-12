package com.mycompany.questionsgenerator.business.interfaces;

import com.mycompany.questionsgenerator.business.models.enums.ChatbotProvider;

public interface IChatbotConfigurationService {

    void activateProvider(ChatbotProvider provider);

    ChatbotProvider getActiveProvider();

}
