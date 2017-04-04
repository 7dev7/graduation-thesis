package com.dev.domain.model.questionnaire;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("INTEGER")
public class IntegerQuestionnaireItem extends QuestionnaireItem {

    private Integer value;

    public IntegerQuestionnaireItem() {
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
