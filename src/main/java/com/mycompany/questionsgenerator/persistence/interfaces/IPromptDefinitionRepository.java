package com.mycompany.questionsgenerator.persistence.interfaces;

import com.mycompany.questionsgenerator.business.models.PromptDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPromptDefinitionRepository extends JpaRepository<PromptDefinition, Long> {

    Optional<PromptDefinition> findByActiveTrue();

}
