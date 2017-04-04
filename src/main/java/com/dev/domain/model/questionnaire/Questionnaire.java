package com.dev.domain.model.questionnaire;

import com.dev.domain.model.Doctor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Questionnaire {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private Doctor author;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionnaire")
    private List<QuestionnaireItem> items = new ArrayList<>();

    public Questionnaire() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<QuestionnaireItem> getItems() {
        return items;
    }

    public void setItems(List<QuestionnaireItem> items) {
        this.items = items;
    }

    public Doctor getAuthor() {
        return author;
    }

    public void setAuthor(Doctor author) {
        this.author = author;
    }
}
