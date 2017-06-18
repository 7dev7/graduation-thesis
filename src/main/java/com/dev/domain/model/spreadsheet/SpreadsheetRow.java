package com.dev.domain.model.spreadsheet;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class SpreadsheetRow implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "spreadsheetId")
    private Spreadsheet spreadsheet;

    private int index;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "spreadsheetRow", cascade = {CascadeType.ALL})
    private List<SpreadsheetCell> spreadsheetCells;

    public SpreadsheetRow(int index, List<SpreadsheetCell> spreadsheetCells) {
        this.index = index;
        this.spreadsheetCells = spreadsheetCells;
    }

    public SpreadsheetRow() {
        this.spreadsheetCells = new ArrayList<>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public List<SpreadsheetCell> getSpreadsheetCells() {
        return spreadsheetCells;
    }

    public void setSpreadsheetCells(List<SpreadsheetCell> spreadsheetCells) {
        this.spreadsheetCells = spreadsheetCells;
    }

    public Map<String, Object> getCellsMap() {
        Map<String, Object> map = new HashMap<>();
        for (SpreadsheetCell cell : spreadsheetCells) {
            map.put(cell.getSpreadsheetColumn().getName(), cell.getValue());
        }
        return map;
    }

    public SpreadsheetCell getCellByColumn(SpreadsheetColumn column) {
        return spreadsheetCells.stream().filter(i -> column.getName().equals(i.getSpreadsheetColumn().getName())).findFirst().orElse(null);
    }
}
