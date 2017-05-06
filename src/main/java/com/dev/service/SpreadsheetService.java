package com.dev.service;

import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.service.exception.StorageException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SpreadsheetService {
    SpreadsheetData getSpreadsheetData(MultipartFile excelFile) throws StorageException;

    List<Spreadsheet> getActiveSpreadsheetsForDoctor(long doctorId);

    List<Spreadsheet> getActiveSpreadsheetsForDoctor(Doctor doctor);

    Spreadsheet createSpreadsheet(MultipartFile excelFile) throws StorageException;
}
