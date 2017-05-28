package com.dev.domain.model.DTO;

import com.dev.domain.model.network.NetworkModelColumnDefinition;

import java.util.ArrayList;
import java.util.List;

public class ComputeResultDTO {
    private long modelId;
    private String modelName;
    private List<Double> values;
    private List<Double> inputValues;

    private List<NetworkModelColumnDefinition> inColumns;
    private List<NetworkModelColumnDefinition> outColumns;

    public ComputeResultDTO() {
        this.values = new ArrayList<>();
        this.inputValues = new ArrayList<>();

        this.inColumns = new ArrayList<>();
        this.outColumns = new ArrayList<>();
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

    public List<NetworkModelColumnDefinition> getInColumns() {
        return inColumns;
    }

    public void setInColumns(List<NetworkModelColumnDefinition> inColumns) {
        this.inColumns = inColumns;
    }

    public List<NetworkModelColumnDefinition> getOutColumns() {
        return outColumns;
    }

    public void setOutColumns(List<NetworkModelColumnDefinition> outColumns) {
        this.outColumns = outColumns;
    }
}
