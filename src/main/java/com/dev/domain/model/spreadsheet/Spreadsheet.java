package com.dev.domain.model.spreadsheet;

import com.dev.domain.model.doctor.Doctor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Spreadsheet implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "spreadsheet", cascade = {CascadeType.ALL})
    private List<SpreadsheetColumn> columns = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "spreadsheet", cascade = {CascadeType.ALL})
    private List<SpreadsheetRow> rows = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor owner;
    private Date lastUpdate;
    private boolean isClosed;

    public Spreadsheet() {
        this.lastUpdate = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Doctor getOwner() {
        return owner;
    }

    public void setOwner(Doctor owner) {
        this.owner = owner;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public List<SpreadsheetColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<SpreadsheetColumn> columns) {
        this.columns = columns;
    }

    public List<SpreadsheetRow> getRows() {
        return rows;
    }

    public void setRows(List<SpreadsheetRow> rows) {
        this.rows = rows;
    }

    public int getNumOfRecords() {
        return rows.size();
    }

    public List<String> getColumnNames() {
        return columns.stream().map(SpreadsheetColumn::getName).collect(Collectors.toList());
    }

    public List<Integer> getColumnIndexes() {
        return columns.stream().map(SpreadsheetColumn::getIndex).collect(Collectors.toList());
    }

    public List<Integer> getColumnTypesIndexes() {
        return columns.stream().map(SpreadsheetColumn::getType).map(ColumnType::ordinal).collect(Collectors.toList());
    }

    public int getMaxRowIndex() {
        return rows.stream().max(Comparator.comparingInt(SpreadsheetRow::getIndex)).orElseGet(SpreadsheetRow::new).getIndex();
    }

    public int getMaxColumnIndex() {
        return columns.stream().max(Comparator.comparingInt(SpreadsheetColumn::getIndex)).orElseGet(SpreadsheetColumn::new).getIndex();
    }
}
