package com.dev.domain.model.spreadsheet;

import java.io.Serializable;

public class SpreadsheetColumn implements Serializable {
    private String name;
    private ColumnType type;

    public SpreadsheetColumn() {

    }

    public SpreadsheetColumn(String name, ColumnType type) {
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
}
