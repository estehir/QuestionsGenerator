package com.mycompany.questionsgenerator.persistence.interfaces;

import com.mycompany.questionsgenerator.business.models.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ITemplateRepository extends JpaRepository<Template, Long> {

    Optional<Template> findByName(String name);

    @Query("""
        select distinct t
        from Template t
        left join fetch t.variables tv
        left join fetch tv.variable
        where t.id = :id
    """)
    Optional<Template> findByIdWithVariables(@Param("id") Long id);

}
