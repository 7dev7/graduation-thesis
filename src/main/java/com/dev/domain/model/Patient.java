package com.dev.domain.model;

import javax.persistence.*;

@Entity
public class Patient {
    @Id
    @GeneratedValue
    private long id;

    private String userLogin;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    public Patient() {
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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
}
