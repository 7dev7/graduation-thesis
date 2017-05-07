package com.dev.domain.model.spreadsheet;

import java.io.Serializable;

public class SpreadsheetColumn implements Serializable {
    private int index;
    private String name;
    private ColumnType type;

    public SpreadsheetColumn() {
    }

    public SpreadsheetColumn(int index, String name, ColumnType type) {
        this.index = index;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
