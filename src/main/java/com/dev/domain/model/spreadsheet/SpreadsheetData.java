package com.dev.domain.model.spreadsheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpreadsheetData implements Serializable {
    private List<SpreadsheetColumn> columns = new ArrayList<>();
    private List<Map<String, Object>> rows = new ArrayList<>();
    private Spreadsheet spreadsheet;

    public List<SpreadsheetColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<SpreadsheetColumn> columns) {
        this.columns = columns;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public int getNumOfRecords() {
        return rows.size();
    }

    public Spreadsheet getSpreadsheet() {
        return spreadsheet;
    }

    public void setSpreadsheet(Spreadsheet spreadsheet) {
        this.spreadsheet = spreadsheet;
    }

    public List<String> getColumnNames() {
        return columns.stream().map(SpreadsheetColumn::getName).collect(Collectors.toList());
    }

    public List<Integer> getColumnTypesIndexes() {
        return columns.stream().map(SpreadsheetColumn::getType).map(ColumnType::ordinal).collect(Collectors.toList());
    }
}
