package com.dev.domain.model;

import com.dev.domain.model.doctor.Doctor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
public class Spreadsheet {
    @Id
    @GeneratedValue
    private long id;
    @Lob
    private byte[] excelFile;
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

    public byte[] getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(byte[] excelFile) {
        this.excelFile = excelFile;
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
                ", excelFile=" + Arrays.toString(excelFile) +
                ", author=" + author +
                ", lastUpdate=" + lastUpdate +
                ", isClosed=" + isClosed +
                '}';
    }
}
