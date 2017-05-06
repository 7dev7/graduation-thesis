package com.dev.service.impl;

import com.dev.domain.dao.SpreadsheetRepository;
import com.dev.domain.model.Spreadsheet;
import com.dev.domain.model.SpreadsheetData;
import com.dev.domain.model.doctor.Doctor;
import com.dev.service.DoctorService;
import com.dev.service.SpreadsheetService;
import com.dev.service.exception.StorageException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpreadsheetServiceImpl implements SpreadsheetService {

    private final SpreadsheetRepository spreadsheetRepository;
    private final DoctorService doctorService;

    @Autowired
    public SpreadsheetServiceImpl(DoctorService doctorService, SpreadsheetRepository spreadsheetRepository) {
        this.doctorService = doctorService;
        this.spreadsheetRepository = spreadsheetRepository;
    }

    @Override
    public SpreadsheetData getSpreadsheetData(MultipartFile excelFile) throws StorageException {
        SpreadsheetData spreadsheetData;
        try {
            Workbook workbook = WorkbookFactory.create(excelFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            spreadsheetData = buildDataWithBasicInfo(sheet);
            spreadsheetData = fillRows(sheet, spreadsheetData);
        } catch (IOException | InvalidFormatException e) {
            throw new StorageException(e.getMessage(), e);
        }
        return spreadsheetData;
    }

    private SpreadsheetData buildDataWithBasicInfo(Sheet sheet) {
        SpreadsheetData spreadsheetData = new SpreadsheetData();
        spreadsheetData.setNumOfRecords(sheet.getLastRowNum() - 1);

        Iterator<Cell> cellIterator = sheet.getRow(0).cellIterator();
        List<String> columns = new ArrayList<>();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String stringCellValue = cell.getStringCellValue();
            if (!StringUtils.isEmpty(stringCellValue)) {
                columns.add(stringCellValue);
            }
        }
        spreadsheetData.setColumns(columns);
        return spreadsheetData;
    }

    private SpreadsheetData fillRows(Sheet sheet, SpreadsheetData spreadsheetData) {
        Iterator<Row> rowIterator = sheet.rowIterator();
        //Miss column name's row
        rowIterator.next();

        List<Map<String, Object>> rows = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();
            int index = 0;
            Map<String, Object> dataRow = new HashMap<>();
            while (cellIterator.hasNext() && index < spreadsheetData.getColumns().size()) {
                Cell cell = cellIterator.next();
                CellType cellTypeEnum = cell.getCellTypeEnum();
                String key = spreadsheetData.getColumns().get(index++);
                switch (cellTypeEnum) {
                    case STRING:
                        dataRow.put(key, cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        double numericCellValue = cell.getNumericCellValue();
                        if (numericCellValue % 1 == 0) {
                            dataRow.put(key, (int) numericCellValue);
                        } else {
                            dataRow.put(key, String.valueOf(numericCellValue));
                        }
                        break;
                }
            }
            rows.add(dataRow);
        }
        spreadsheetData.setRows(rows);
        return spreadsheetData;
    }

    @Override
    public List<Spreadsheet> getActiveSpreadsheetsForDoctor(long doctorId) {
        Doctor author = doctorService.findById(doctorId);
        List<Spreadsheet> spreadsheetsByAuthor = spreadsheetRepository.getSpreadsheetsByAuthor(author);
        return spreadsheetsByAuthor.stream().filter(i -> !i.isClosed()).collect(Collectors.toList());
    }

    @Override
    public List<Spreadsheet> getActiveSpreadsheetsForDoctor(Doctor doctor) {
        List<Spreadsheet> spreadsheetsByAuthor = spreadsheetRepository.getSpreadsheetsByAuthor(doctor);
        return spreadsheetsByAuthor.stream().filter(i -> !i.isClosed()).collect(Collectors.toList());
    }

    @Override
    public void saveSpreadsheet(MultipartFile file) {
        try {
            Spreadsheet spreadsheet = new Spreadsheet();
            spreadsheet.setClosed(false);
            spreadsheet.setExcelFile(file.getBytes());
            spreadsheet.setAuthor(doctorService.getCurrentDoctor());
            spreadsheetRepository.save(spreadsheet);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}