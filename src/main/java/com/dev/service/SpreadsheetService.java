package com.dev.service;

import com.dev.domain.model.DTO.ValidateInputsDTO;
import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.model.spreadsheet.ColumnType;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.service.exception.StorageException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;


public interface SpreadsheetService {
    Optional<Spreadsheet> getActiveSpreadsheetForDoctor(long doctorId);

    Optional<Spreadsheet> getActiveSpreadsheetForDoctor(Doctor doctor);

    Optional<Spreadsheet> getActiveSpreadsheetForCurrentDoctor();

    Spreadsheet createSpreadsheet(MultipartFile excelFile) throws StorageException;

    Spreadsheet createSpreadsheet();

    void updateSpreadsheet(Spreadsheet spreadsheet);

    void removeColumnByIndex(int index, String initName) throws StorageException;

    void updateColumn(int index, String initName, String name, ColumnType type) throws StorageException;

    int addRow(Map<String, Object> paramsMap) throws StorageException;

    void removeRowByIndex(int index) throws StorageException;

    void editRowByIndex(int index, Map<String, Object> paramsMap) throws StorageException;

    boolean validate(ValidateInputsDTO validateInputsDTO);
}
