package com.dev.domain.model.user;

import com.dev.domain.model.Patient;
import com.dev.domain.model.questionnaire.Questionnaire;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String login;
    private String name;
    private String password;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private List<Patient> patients;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "items")
    private List<Questionnaire> questionnaires;

    public Doctor() {
        patients = new ArrayList<>();
        questionnaires = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Questionnaire> getQuestionnaires() {
        return questionnaires;
    }

    public void setQuestionnaires(List<Questionnaire> questionnaires) {
        this.questionnaires = questionnaires;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", enabled=" + enabled +
                '}';
    }
}