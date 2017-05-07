package com.dev.domain.model.DTO;

import net.minidev.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SpreadsheetDataDTO {
    private List<String> columns = new ArrayList<>();
    private List<Integer> columnTypes = new ArrayList<>();
    private int numOfRecords;
    private JSONArray rows;

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public int getNumOfRecords() {
        return numOfRecords;
    }

    public void setNumOfRecords(int numOfRecords) {
        this.numOfRecords = numOfRecords;
    }

    public JSONArray getRows() {
        return rows;
    }

    public void setRows(JSONArray rows) {
        this.rows = rows;
    }

    public List<Integer> getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(List<Integer> columnTypes) {
        this.columnTypes = columnTypes;
    }

    @Override
    public String toString() {
        return "SpreadsheetDataDTO{" +
                "columns=" + columns +
                ", numOfRecords=" + numOfRecords +
                ", rows=" + rows +
                '}';
    }
}
