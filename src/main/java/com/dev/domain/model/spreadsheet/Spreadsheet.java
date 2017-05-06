package com.dev.domain.model.spreadsheet;

import com.dev.domain.model.doctor.Doctor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Spreadsheet implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Lob
    private SpreadsheetData spreadsheetData;
    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor author;
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

    public SpreadsheetData getSpreadsheetData() {
        return spreadsheetData;
    }

    public void setSpreadsheetData(SpreadsheetData spreadsheetData) {
        this.spreadsheetData = spreadsheetData;
    }

    public Doctor getAuthor() {
        return author;
    }

    public void setAuthor(Doctor author) {
        this.author = author;
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

    @Override
    public String toString() {
        return "Spreadsheet{" +
                "id=" + id +
                ", spreadsheetData=" + spreadsheetData +
                ", author=" + author +
                ", lastUpdate=" + lastUpdate +
                ", isClosed=" + isClosed +
                '}';
    }
}
