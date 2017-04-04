package com.dev.domain.model.questionnaire;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DOUBLE")
public class DoubleQuestionnaireItem extends QuestionnaireItem {

    private double value;

    public DoubleQuestionnaireItem() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
