package com.dev.domain.model.questionnaire;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TEXT")
public class TextQuestionnaireItem extends QuestionnaireItem {

    private String value;

    public TextQuestionnaireItem() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
