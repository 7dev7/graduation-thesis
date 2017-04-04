package com.dev.domain.model.questionnaire;

import com.dev.domain.model.feature.Feature;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CLASS_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class QuestionnaireItem {
    @Id
    @GeneratedValue
    protected long id;

    @ManyToOne
    @JoinColumn(name = "featureId")
    protected Feature feature;

    protected String question;

    @ManyToOne
    @JoinColumn(name = "questionnaireId")
    private Questionnaire questionnaire;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }
}
