package com.dev.service;

import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.service.exception.StorageException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


public interface SpreadsheetService {
    SpreadsheetData getSpreadsheetData(MultipartFile excelFile) throws StorageException;

    Optional<Spreadsheet> getActiveSpreadsheetForDoctor(long doctorId);

    Optional<Spreadsheet> getActiveSpreadsheetForDoctor(Doctor doctor);

    Optional<Spreadsheet> getActiveSpreadsheetForCurrentDoctor();

    Spreadsheet createSpreadsheet(MultipartFile excelFile) throws StorageException;

    void updateSpreadsheet(Spreadsheet spreadsheet);
}
