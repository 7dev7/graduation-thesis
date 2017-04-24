package com.dev.domain.model;

import com.dev.domain.model.doctor.Doctor;

import javax.persistence.*;

@Entity
public class Patient {
    @Id
    @GeneratedValue
    private long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
