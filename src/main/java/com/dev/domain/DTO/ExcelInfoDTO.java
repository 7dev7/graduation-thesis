package com.dev.domain.DTO;

import net.minidev.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ExcelInfoDTO {
    private List<String> columns = new ArrayList<>();
    private int records;
    private int total;
    private JSONArray rows;

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public JSONArray getRows() {
        return rows;
    }

    public void setRows(JSONArray rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "ExcelInfoDTO{" +
                "columns=" + columns +
                ", records=" + records +
                ", total=" + total +
                ", rows=" + rows +
                '}';
    }
}
