package com.dev.domain.model.DTO;

import java.util.ArrayList;
import java.util.List;

public class ValidateInputsDTO {
    private List<Integer> columnIndexes;

    public ValidateInputsDTO() {
        this.columnIndexes = new ArrayList<>();
    }

    public List<Integer> getColumnIndexes() {
        return columnIndexes;
    }

    public void setColumnIndexes(List<Integer> columnIndexes) {
        this.columnIndexes = columnIndexes;
    }
}
