package com.mycompany.questionsgenerator.persistence.repositories;

import com.mycompany.questionsgenerator.business.models.Template;
import com.mycompany.questionsgenerator.persistence.interfaces.ITemplateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long>, ITemplateRepository {

    boolean existsByName(String name);

}
