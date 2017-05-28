package com.dev.domain.model.spreadsheet;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SpreadsheetColumn implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private int index;
    private String name;
    private ColumnType type;
    private MeasurementType measurementType;

    @ManyToOne
    @JoinColumn(name = "spreadsheetId")
    private Spreadsheet spreadsheet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "spreadsheetColumn", cascade = {CascadeType.ALL})
    private List<SpreadsheetCell> spreadsheetCells;

    public SpreadsheetColumn() {
        this.spreadsheetCells = new ArrayList<>();
    }

    public SpreadsheetColumn(int index, String name, ColumnType type) {
        this.spreadsheetCells = new ArrayList<>();
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

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public List<SpreadsheetCell> getSpreadsheetCells() {
        return spreadsheetCells;
    }

    public void setSpreadsheetCells(List<SpreadsheetCell> spreadsheetCells) {
        this.spreadsheetCells = spreadsheetCells;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Spreadsheet getSpreadsheet() {
        return spreadsheet;
    }

    public void setSpreadsheet(Spreadsheet spreadsheet) {
        this.spreadsheet = spreadsheet;
    }
}
