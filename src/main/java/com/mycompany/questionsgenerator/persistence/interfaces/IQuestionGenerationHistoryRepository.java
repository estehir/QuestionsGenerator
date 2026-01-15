package com.mycompany.questionsgenerator.persistence.interfaces;

import com.mycompany.questionsgenerator.business.models.QuestionGenerationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionGenerationHistoryRepository extends JpaRepository<QuestionGenerationHistory, Long> {

}

