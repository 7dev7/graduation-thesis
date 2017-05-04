package com.dev.service;

import com.dev.domain.model.Spreadsheet;
import com.dev.domain.model.doctor.Doctor;

import java.io.File;
import java.util.List;

public interface SpreadsheetService {
    List<Spreadsheet> getActiveSpreadsheetsForDoctor(long doctorId);

    List<Spreadsheet> getActiveSpreadsheetsForDoctor(Doctor doctor);

    void saveSpreadsheet(File file);
}
