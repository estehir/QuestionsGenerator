package com.mycompany.questionsgenerator.business.services;

import com.mycompany.questionsgenerator.business.interfaces.IChatbotConfigurationService;
import com.mycompany.questionsgenerator.business.models.ChatbotConfiguration;
import com.mycompany.questionsgenerator.business.models.enums.ChatbotProvider;
import com.mycompany.questionsgenerator.persistence.interfaces.IChatbotConfigurationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatbotConfigurationService implements IChatbotConfigurationService {

    private final IChatbotConfigurationRepository repository;

    @Override
    @Transactional
    public void activateProvider(ChatbotProvider provider) {

        // Desativa o atual (se existir)
        repository.findByActiveTrue().ifPresent((ChatbotConfiguration cfg) ->
                cfg.setActive(false));

        // Ativa ou cria o novo
        ChatbotConfiguration config = repository.findByProvider(provider)
                .orElseGet(() -> {
                    ChatbotConfiguration c = new ChatbotConfiguration();
                    c.setProvider(provider);
                    return c;
                });

        config.setActive(true);

        repository.save(config);
        repository.flush();
    }

    @Override
    public ChatbotProvider getActiveProvider() {
        return repository.findByActiveTrue()
                .map(ChatbotConfiguration::getProvider)
                .orElseThrow(() -> new IllegalStateException(
                        "No active chatbot configured"
                ));
    }
}
