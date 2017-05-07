package com.dev.domain.model.spreadsheet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SpreadsheetRow implements Serializable {
    private int index;
    private Map<String, Object> elements;

    public SpreadsheetRow(int index, Map<String, Object> elements) {
        this.index = index;
        this.elements = elements;
    }

    public SpreadsheetRow() {
        this.elements = new HashMap<>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Map<String, Object> getElements() {
        return elements;
    }

    public void setElements(Map<String, Object> elements) {
        this.elements = elements;
    }
}
