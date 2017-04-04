package com.dev.domain.dao;

import com.dev.domain.model.questionnaire.Questionnaire;
import org.springframework.data.repository.CrudRepository;

public interface QuestionnaireRepository extends CrudRepository<Questionnaire, Long> {

}
