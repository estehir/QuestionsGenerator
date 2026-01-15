package com.mycompany.questionsgenerator.business.services;

import com.mycompany.questionsgenerator.api.dtos.PromptDefinitionRequestDTO;
import com.mycompany.questionsgenerator.api.dtos.PromptDefinitionResponseDTO;
import com.mycompany.questionsgenerator.business.interfaces.IPromptDefinitionService;
import com.mycompany.questionsgenerator.business.models.PromptDefinition;
import com.mycompany.questionsgenerator.persistence.interfaces.IPromptDefinitionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PromptDefinitionService implements IPromptDefinitionService {

    private final IPromptDefinitionRepository repository;

    @Override
    public PromptDefinitionResponseDTO create(PromptDefinitionRequestDTO request) {

        int nextVersion = repository.findMaxVersion().orElse(0) + 1;

        PromptDefinition prompt = PromptDefinition.builder()
                .version(nextVersion)
                .description(request.getDescription())
                .text(request.getText())
                .active(false)
                .build();

        return toResponse(repository.save(prompt));
    }

    @Override
    @CacheEvict(value = "activePromptDefinition", allEntries = true)
    public PromptDefinitionResponseDTO activate(Long id) {

        repository.findByActiveTrue()
                .ifPresent(p -> p.setActive(false));

        PromptDefinition prompt = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));

        prompt.setActive(true);

        return toResponse(prompt);
    }

    @Override
    @Cacheable("activePromptDefinition")
    public PromptDefinitionResponseDTO getActive() {
        return repository.findByActiveTrue()
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalStateException("No active prompt"));
    }

    @Override
    public List<PromptDefinitionResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private PromptDefinitionResponseDTO toResponse(PromptDefinition p) {
        return PromptDefinitionResponseDTO.builder()
                .id(p.getId())
                .version(p.getVersion())
                .description(p.getDescription())
                .text(p.getText())
                .active(p.getActive())
                .createdAt(p.getCreatedAt())
                .build();
    }
}

