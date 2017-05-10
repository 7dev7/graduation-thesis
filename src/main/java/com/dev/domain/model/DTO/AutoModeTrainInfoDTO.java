package com.dev.domain.model.DTO;

import com.dev.domain.model.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class AutoModeTrainInfoDTO {
    private List<Integer> inputContinuousColumnIndexes;
    private List<Integer> inputCategorialColumnIndexes;
    private List<Integer> outputContinuousColumnIndexes;

    private Integer numOfSavedNetworks;

    private Boolean isMLPNeeded;
    private Integer mlpMinNumOfNeuron;
    private Integer mlpMaxNumOfNeuron;

    private Boolean isRBFNeeded;
    private Integer rbfMinNumOfNeuron;
    private Integer rbfMaxNumOfNeuron;

    private List<ActivationFunction> hiddenNeuronsFuncs;
    private List<ActivationFunction> outNeuronsFuncs;

    public AutoModeTrainInfoDTO() {
        this.inputContinuousColumnIndexes = new ArrayList<>();
        this.inputCategorialColumnIndexes = new ArrayList<>();
        this.outputContinuousColumnIndexes = new ArrayList<>();
        this.hiddenNeuronsFuncs = new ArrayList<>();
        this.outNeuronsFuncs = new ArrayList<>();
    }

    public List<Integer> getInputContinuousColumnIndexes() {
        return inputContinuousColumnIndexes;
    }

    public void setInputContinuousColumnIndexes(List<Integer> inputContinuousColumnIndexes) {
        this.inputContinuousColumnIndexes = inputContinuousColumnIndexes;
    }

    public List<Integer> getOutputContinuousColumnIndexes() {
        return outputContinuousColumnIndexes;
    }

    public void setOutputContinuousColumnIndexes(List<Integer> outputContinuousColumnIndexes) {
        this.outputContinuousColumnIndexes = outputContinuousColumnIndexes;
    }

    public Integer getMlpMinNumOfNeuron() {
        return mlpMinNumOfNeuron;
    }

    public void setMlpMinNumOfNeuron(Integer mlpMinNumOfNeuron) {
        this.mlpMinNumOfNeuron = mlpMinNumOfNeuron;
    }

    public Integer getMlpMaxNumOfNeuron() {
        return mlpMaxNumOfNeuron;
    }

    public void setMlpMaxNumOfNeuron(Integer mlpMaxNumOfNeuron) {
        this.mlpMaxNumOfNeuron = mlpMaxNumOfNeuron;
    }

    public Integer getRbfMinNumOfNeuron() {
        return rbfMinNumOfNeuron;
    }

    public void setRbfMinNumOfNeuron(Integer rbfMinNumOfNeuron) {
        this.rbfMinNumOfNeuron = rbfMinNumOfNeuron;
    }

    public Integer getRbfMaxNumOfNeuron() {
        return rbfMaxNumOfNeuron;
    }

    public void setRbfMaxNumOfNeuron(Integer rbfMaxNumOfNeuron) {
        this.rbfMaxNumOfNeuron = rbfMaxNumOfNeuron;
    }

    public List<ActivationFunction> getHiddenNeuronsFuncs() {
        return hiddenNeuronsFuncs;
    }

    public void setHiddenNeuronsFuncs(List<ActivationFunction> hiddenNeuronsFuncs) {
        this.hiddenNeuronsFuncs = hiddenNeuronsFuncs;
    }

    public List<ActivationFunction> getOutNeuronsFuncs() {
        return outNeuronsFuncs;
    }

    public void setOutNeuronsFuncs(List<ActivationFunction> outNeuronsFuncs) {
        this.outNeuronsFuncs = outNeuronsFuncs;
    }

    public Boolean getIsMLPNeeded() {
        return isMLPNeeded;
    }

    public void setIsMLPNeeded(Boolean isMLPNeeded) {
        this.isMLPNeeded = isMLPNeeded;
    }

    public Boolean getIsRBFNeeded() {
        return isRBFNeeded;
    }

    public void setIsRBFNeeded(Boolean isRBFNeeded) {
        this.isRBFNeeded = isRBFNeeded;
    }

    public List<Integer> getInputCategorialColumnIndexes() {
        return inputCategorialColumnIndexes;
    }

    public void setInputCategorialColumnIndexes(List<Integer> inputCategorialColumnIndexes) {
        this.inputCategorialColumnIndexes = inputCategorialColumnIndexes;
    }

    public Integer getNumOfSavedNetworks() {
        return numOfSavedNetworks;
    }

    public void setNumOfSavedNetworks(Integer numOfSavedNetworks) {
        this.numOfSavedNetworks = numOfSavedNetworks;
    }

    @Override
    public String toString() {
        return "AutoModeTrainInfoDTO{" +
                "inputContinuousColumnIndexes=" + inputContinuousColumnIndexes +
                ", inputCategorialColumnIndexes=" + inputCategorialColumnIndexes +
                ", outputContinuousColumnIndexes=" + outputContinuousColumnIndexes +
                ", numOfSavedNetworks=" + numOfSavedNetworks +
                ", isMLPNeeded=" + isMLPNeeded +
                ", mlpMinNumOfNeuron=" + mlpMinNumOfNeuron +
                ", mlpMaxNumOfNeuron=" + mlpMaxNumOfNeuron +
                ", isRBFNeeded=" + isRBFNeeded +
                ", rbfMinNumOfNeuron=" + rbfMinNumOfNeuron +
                ", rbfMaxNumOfNeuron=" + rbfMaxNumOfNeuron +
                ", hiddenNeuronsFuncs=" + hiddenNeuronsFuncs +
                ", outNeuronsFuncs=" + outNeuronsFuncs +
                '}';
    }
}
