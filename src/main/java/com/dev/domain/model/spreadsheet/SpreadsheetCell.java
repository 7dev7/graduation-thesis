package com.dev.domain.model.spreadsheet;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SpreadsheetCell implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "spreadsheetColumnId")
    private SpreadsheetColumn spreadsheetColumn;

    @ManyToOne
    @JoinColumn(name = "spreadsheetRowId")
    private SpreadsheetRow spreadsheetRow;

    private String value;

    public SpreadsheetCell() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SpreadsheetColumn getSpreadsheetColumn() {
        return spreadsheetColumn;
    }

    public void setSpreadsheetColumn(SpreadsheetColumn spreadsheetColumn) {
        this.spreadsheetColumn = spreadsheetColumn;
    }

    public SpreadsheetRow getSpreadsheetRow() {
        return spreadsheetRow;
    }

    public void setSpreadsheetRow(SpreadsheetRow spreadsheetRow) {
        this.spreadsheetRow = spreadsheetRow;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
