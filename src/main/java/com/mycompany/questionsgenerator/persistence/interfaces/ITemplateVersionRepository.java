package com.mycompany.questionsgenerator.persistence.interfaces;

import com.mycompany.questionsgenerator.business.models.TemplateVersion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ITemplateVersionRepository extends JpaRepository<TemplateVersion, Long> {

    Optional<TemplateVersion> findByTemplateIdAndVersion(
            Long templateId,
            Integer version
    );

    @Query("""
        select distinct tv
        from TemplateVersion tv
        left join fetch tv.variables v
        left join fetch v.variable
        left join fetch v.variable.options
        where tv.template.id = :templateId
    """)
    List<TemplateVersion> findByTemplateId(Long templateId, Pageable pageable);

}

