package com.mycompany.questionsgenerator.persistence.interfaces;

import com.mycompany.questionsgenerator.business.models.VariableOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVariableOptionRepository
        extends JpaRepository<VariableOption, Long> { }
