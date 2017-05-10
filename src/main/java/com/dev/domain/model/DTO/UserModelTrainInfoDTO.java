package com.dev.domain.model.DTO;

import com.dev.domain.model.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class UserModelTrainInfoDTO {
    private List<Integer> inputContinuousColumnIndexes;
    private List<Integer> inputCategorialColumnIndexes;
    private List<Integer> outputContinuousColumnIndexes;

    private Boolean isMLPModel;
    private Integer numOfNeurons;
    private ActivationFunction hiddenActivationFunction;
    private ActivationFunction outActivationFunction;

    public UserModelTrainInfoDTO() {
        this.inputContinuousColumnIndexes = new ArrayList<>();
        this.outputContinuousColumnIndexes = new ArrayList<>();
    }

    public List<Integer> getInputContinuousColumnIndexes() {
        return inputContinuousColumnIndexes;
    }

    public void setInputContinuousColumnIndexes(List<Integer> inputContinuousColumnIndexes) {
        this.inputContinuousColumnIndexes = inputContinuousColumnIndexes;
    }

    public List<Integer> getInputCategorialColumnIndexes() {
        return inputCategorialColumnIndexes;
    }

    public void setInputCategorialColumnIndexes(List<Integer> inputCategorialColumnIndexes) {
        this.inputCategorialColumnIndexes = inputCategorialColumnIndexes;
    }

    public List<Integer> getOutputContinuousColumnIndexes() {
        return outputContinuousColumnIndexes;
    }

    public void setOutputContinuousColumnIndexes(List<Integer> outputContinuousColumnIndexes) {
        this.outputContinuousColumnIndexes = outputContinuousColumnIndexes;
    }

    public Integer getNumOfNeurons() {
        return numOfNeurons;
    }

    public void setNumOfNeurons(Integer numOfNeurons) {
        this.numOfNeurons = numOfNeurons;
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

    public Boolean getIsMLPModel() {
        return isMLPModel;
    }

    public void setIsMLPModel(Boolean isMLPModel) {
        this.isMLPModel = isMLPModel;
    }

    @Override
    public String toString() {
        return "UserModelTrainInfoDTO{" +
                "inputContinuousColumnIndexes=" + inputContinuousColumnIndexes +
                ", inputCategorialColumnIndexes=" + inputCategorialColumnIndexes +
                ", outputContinuousColumnIndexes=" + outputContinuousColumnIndexes +
                ", isMLPModel=" + isMLPModel +
                ", numOfNeurons=" + numOfNeurons +
                ", hiddenActivationFunction=" + hiddenActivationFunction +
                ", outActivationFunction=" + outActivationFunction +
                '}';
    }
}
