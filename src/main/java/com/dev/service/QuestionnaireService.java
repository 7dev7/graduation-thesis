package com.dev.service;

import com.dev.domain.dao.FeatureRepository;
import com.dev.domain.dao.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;
    private final FeatureRepository featureRepository;

    @Autowired
    public QuestionnaireService(QuestionnaireRepository questionnaireRepository, FeatureRepository featureRepository) {
        this.questionnaireRepository = questionnaireRepository;
        this.featureRepository = featureRepository;
    }


}
