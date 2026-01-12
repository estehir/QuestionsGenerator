package com.mycompany.questionsgenerator.persistence.interfaces;

import com.mycompany.questionsgenerator.business.models.ChatbotConfiguration;
import com.mycompany.questionsgenerator.business.models.enums.ChatbotProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IChatbotConfigurationRepository
        extends JpaRepository<ChatbotConfiguration, Long> {

    Optional<ChatbotConfiguration> findByActiveTrue();

    Optional<ChatbotConfiguration> findByProvider(ChatbotProvider provider);
}
