package com.dev.domain.model.DTO;

import com.dev.domain.model.ActivationFunction;

import java.io.Serializable;

public class NetworkModelDTO implements Serializable {
    private long id;
    private String name;
    private boolean isPerceptronModel;
    private double error;
    private ActivationFunction hiddenActivationFunction;
    private ActivationFunction outActivationFunction;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPerceptronModel() {
        return isPerceptronModel;
    }

    public void setPerceptronModel(boolean perceptronModel) {
        isPerceptronModel = perceptronModel;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NetworkModelDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isPerceptronModel=" + isPerceptronModel +
                ", error=" + error +
                ", hiddenActivationFunction=" + hiddenActivationFunction +
                ", outActivationFunction=" + outActivationFunction +
                '}';
    }
}
