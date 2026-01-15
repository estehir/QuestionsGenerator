package com.mycompany.questionsgenerator.persistence.interfaces;

import com.mycompany.questionsgenerator.business.models.PromptDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IPromptDefinitionRepository extends JpaRepository<PromptDefinition, Long> {

    Optional<PromptDefinition> findByActiveTrue();

    @Query("select max(p.version) from PromptDefinition p")
    Optional<Integer> findMaxVersion();
}
