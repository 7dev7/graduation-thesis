package com.dev.service;

import com.dev.domain.model.Spreadsheet;
import com.dev.domain.model.SpreadsheetData;
import com.dev.domain.model.doctor.Doctor;
import com.dev.service.exception.StorageException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SpreadsheetService {
    SpreadsheetData getSpreadsheetData(MultipartFile excelFile) throws StorageException;

    List<Spreadsheet> getActiveSpreadsheetsForDoctor(long doctorId);

    List<Spreadsheet> getActiveSpreadsheetsForDoctor(Doctor doctor);

    void saveSpreadsheet(MultipartFile file);
}
