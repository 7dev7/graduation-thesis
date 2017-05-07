package com.dev.domain.model;

import com.dev.domain.model.perceptron.Perceptron;

public class TrainedNetworkInfo {
    private long id;
    private String name;
    private Perceptron perceptron;
    private double error;
    private ActivationFunction hiddenActivationFunction;
    private ActivationFunction outActivationFunction;

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
}
