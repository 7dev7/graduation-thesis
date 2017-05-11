package com.dev.domain.model.DTO;

import java.util.ArrayList;
import java.util.List;

public class ComputeModelDataDTO {
    private long modelId;
    private List<Double> inputs;

    public ComputeModelDataDTO() {
        this.inputs = new ArrayList<>();
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public List<Double> getInputs() {
        return inputs;
    }

    public void setInputs(List<Double> inputs) {
        this.inputs = inputs;
    }
}
