package com.dev.domain.model.DTO;

import com.dev.domain.model.spreadsheet.MeasurementType;

import java.util.ArrayList;
import java.util.List;

public class ComputeResultDTO {
    private long modelId;
    private String modelName;
    private List<Double> values;
    private List<Double> inputValues;

    private List<String> inColumns;
    private List<String> outColumns;

    private List<MeasurementType> measurementTypes;

    public ComputeResultDTO() {
        this.values = new ArrayList<>();
        this.inputValues = new ArrayList<>();

        this.inColumns = new ArrayList<>();
        this.outColumns = new ArrayList<>();
        this.measurementTypes = new ArrayList<>();
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

    public List<String> getInColumns() {
        return inColumns;
    }

    public void setInColumns(List<String> inColumns) {
        this.inColumns = inColumns;
    }

    public List<String> getOutColumns() {
        return outColumns;
    }

    public void setOutColumns(List<String> outColumns) {
        this.outColumns = outColumns;
    }

    public List<MeasurementType> getMeasurementTypes() {
        return measurementTypes;
    }

    public void setMeasurementTypes(List<MeasurementType> measurementTypes) {
        this.measurementTypes = measurementTypes;
    }
}
