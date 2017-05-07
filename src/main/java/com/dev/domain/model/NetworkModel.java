package com.dev.domain.model;

import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.model.perceptron.Perceptron;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class NetworkModel implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    @Lob
    private Perceptron perceptron;

    private double error;
    private ActivationFunction hiddenActivationFunction;
    private ActivationFunction outActivationFunction;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Perceptron getPerceptron() {
        return perceptron;
    }

    public void setPerceptron(Perceptron perceptron) {
        this.perceptron = perceptron;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public ActivationFunction getHiddenActivationFunction() {
        return hiddenActivationFunction;
    }

    public void setHiddenActivationFunction(ActivationFunction hiddenActivationFunction) {
        this.hiddenActivationFunction = hiddenActivationFunction;
    }

    public ActivationFunction getOutActivationFunction() {
        return outActivationFunction;
    }

    public void setOutActivationFunction(ActivationFunction outActivationFunction) {
        this.outActivationFunction = outActivationFunction;
    }

    public Doctor getOwner() {
        return owner;
    }

    public void setOwner(Doctor owner) {
        this.owner = owner;
    }
}
