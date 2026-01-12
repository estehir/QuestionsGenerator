package com.mycompany.questionsgenerator.business.chatbot;

import com.mycompany.questionsgenerator.business.interfaces.IChatbotConfigurationService;
import com.mycompany.questionsgenerator.business.models.enums.ChatbotProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ChatbotClientResolver {

    private final IChatbotConfigurationService configurationService;
    private final Map<ChatbotProvider, ChatbotClient> clientsByProvider;

    public ChatbotClientResolver(
            IChatbotConfigurationService configurationService,
            List<ChatbotClient> clients
    ) {
        this.configurationService = configurationService;
        this.clientsByProvider = clients.stream()
                .collect(Collectors.toMap(
                        ChatbotClient::getProvider,
                        Function.identity()
                ));
    }

    public ChatbotClient resolveActiveClient() {
        ChatbotProvider activeProvider =
                configurationService.getActiveProvider();

        ChatbotClient client = clientsByProvider.get(activeProvider);

        if (client == null) {
            throw new IllegalStateException(
                    "No ChatbotClient implementation found for provider: " + activeProvider
            );
        }

        return client;
    }
}
