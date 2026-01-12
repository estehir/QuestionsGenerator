package com.mycompany.questionsgenerator.persistence.interfaces;

import com.mycompany.questionsgenerator.business.models.Variable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IVariableRepository extends JpaRepository<Variable, Long> {

    List<Variable> findByCodeIn(Collection<String> codes);

    Optional<Variable> findByCode(String code);

    @Query("""
        select distinct v
        from Variable v
        left join fetch v.options
        where v.code = :code
    """)
    Optional<Variable> findByCodeWithOptions(@Param("code") String code);

    @Query("""
        select distinct v
        from Variable v
        left join fetch v.options
    """)
    List<Variable> findAllWithOptions();


    boolean existsByCode(String code);

}
