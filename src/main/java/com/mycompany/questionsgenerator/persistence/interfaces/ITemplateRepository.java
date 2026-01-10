package com.mycompany.questionsgenerator.persistence.interfaces;

import com.mycompany.questionsgenerator.business.models.Template;

import java.util.Optional;

public interface ITemplateRepository {
    Template save(Template template);

    Optional<Template> findById(Long id);

    boolean existsByName(String name);

}
