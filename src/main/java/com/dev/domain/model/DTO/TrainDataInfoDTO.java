package com.dev.domain.model.DTO;

import org.encog.ml.data.MLDataSet;

import java.util.ArrayList;
import java.util.List;

public class TrainDataInfoDTO {
    private MLDataSet mlDataSet;
    private List<Double> maxIns;
    private List<Double> minIns;

    private List<Double> maxOuts;
    private List<Double> minOuts;

    private List<String> inputColumns;
    private List<String> outColumns;

    public TrainDataInfoDTO() {
        maxIns = new ArrayList<>();
        minIns = new ArrayList<>();

        maxOuts = new ArrayList<>();
        minOuts = new ArrayList<>();

        inputColumns = new ArrayList<>();
        outColumns = new ArrayList<>();
    }

    public MLDataSet getMlDataSet() {
        return mlDataSet;
    }

    public void setMlDataSet(MLDataSet mlDataSet) {
        this.mlDataSet = mlDataSet;
    }

    public List<Double> getMaxIns() {
        return maxIns;
    }

    public void setMaxIns(List<Double> maxIns) {
        this.maxIns = maxIns;
    }

    public List<Double> getMinIns() {
        return minIns;
    }

    public void setMinIns(List<Double> minIns) {
        this.minIns = minIns;
    }

    public List<Double> getMaxOuts() {
        return maxOuts;
    }

    public void setMaxOuts(List<Double> maxOuts) {
        this.maxOuts = maxOuts;
    }

    public List<Double> getMinOuts() {
        return minOuts;
    }

    public void setMinOuts(List<Double> minOuts) {
        this.minOuts = minOuts;
    }

    public List<String> getInputColumns() {
        return inputColumns;
    }

    public void setInputColumns(List<String> inputColumns) {
        this.inputColumns = inputColumns;
    }

    public List<String> getOutColumns() {
        return outColumns;
    }

    public void setOutColumns(List<String> outColumns) {
        this.outColumns = outColumns;
    }
}
