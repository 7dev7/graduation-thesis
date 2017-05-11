package com.dev.service;

import com.dev.domain.model.DTO.ValidateInputsDTO;
import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.model.spreadsheet.ColumnType;
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

    Spreadsheet createSpreadsheet();

    void updateSpreadsheet(Spreadsheet spreadsheet);

    void removeColumnByIndex(int index, String initName) throws StorageException;

    void updateColumn(int index, String initName, String name, ColumnType type) throws StorageException;

    boolean validate(ValidateInputsDTO validateInputsDTO);
}
