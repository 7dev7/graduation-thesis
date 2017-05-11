package com.dev.domain.model.DTO;

import com.dev.domain.model.ActivationFunction;

import java.io.Serializable;
import java.util.Date;

public class NetworkModelDTO implements Serializable {
    private long id;
    private String name;
    private boolean isPerceptronModel;
    private double error;
    private String description;
    private Date dateOfCreation;
    private ActivationFunction hiddenActivationFunction;
    private ActivationFunction outActivationFunction;

    private String hiddenFuncFormatted;
    private String outFuncFormatted;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getHiddenFuncFormatted() {
        return hiddenFuncFormatted;
    }

    public void setHiddenFuncFormatted(String hiddenFuncFormatted) {
        this.hiddenFuncFormatted = hiddenFuncFormatted;
    }

    public String getOutFuncFormatted() {
        return outFuncFormatted;
    }

    public void setOutFuncFormatted(String outFuncFormatted) {
        this.outFuncFormatted = outFuncFormatted;
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
