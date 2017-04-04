package com.dev.domain.model;

import com.dev.domain.model.questionnaire.Questionnaire;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor {
    @Id
    @GeneratedValue
    private long id;

    private String userLogin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private List<Patient> patients;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "items")
    private List<Questionnaire> questionnaires;

    public Doctor() {
        patients = new ArrayList<>();
        questionnaires = new ArrayList<>();
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Questionnaire> getQuestionnaires() {
        return questionnaires;
    }

    public void setQuestionnaires(List<Questionnaire> questionnaires) {
        this.questionnaires = questionnaires;
    }
}
