package com.dev.domain.model.DTO;

import java.util.ArrayList;
import java.util.List;

public class SaveTrainedModelsDTO {
    private List<Long> selectedModels = new ArrayList<>();
    private List<Long> nonSelectedModels = new ArrayList<>();

    public SaveTrainedModelsDTO() {
    }

    public List<Long> getSelectedModels() {
        return selectedModels;
    }

    public void setSelectedModels(List<Long> selectedModels) {
        this.selectedModels = selectedModels;
    }

    public List<Long> getNonSelectedModels() {
        return nonSelectedModels;
    }

    public void setNonSelectedModels(List<Long> nonSelectedModels) {
        this.nonSelectedModels = nonSelectedModels;
    }
}
