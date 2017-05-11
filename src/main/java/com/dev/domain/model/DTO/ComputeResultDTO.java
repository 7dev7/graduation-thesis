package com.dev.domain.model.DTO;

import java.util.ArrayList;
import java.util.List;

public class ComputeResultDTO {
    private List<Double> values;
    private List<Double> inputValues;
    private long modelId;
    private String modelName;

    public ComputeResultDTO() {
        this.values = new ArrayList<>();
        this.inputValues = new ArrayList<>();
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public List<Double> getInputValues() {
        return inputValues;
    }

    public void setInputValues(List<Double> inputValues) {
        this.inputValues = inputValues;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
