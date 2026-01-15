package com.mycompany.questionsgenerator.persistence.interfaces;

import com.mycompany.questionsgenerator.business.models.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITemplateRepository extends JpaRepository<Template, Long> {

    Optional<Template> findByName(String name);

}
