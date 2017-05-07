package com.dev.service.impl;

import com.dev.domain.dao.SpreadsheetRepository;
import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.model.spreadsheet.*;
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
        spreadsheetData.setColumns(buildColumns(sheet));
        return spreadsheetData;
    }

    private List<SpreadsheetColumn> buildColumns(Sheet sheet) {
        Iterator<Cell> cellIterator = sheet.getRow(0).cellIterator();
        List<SpreadsheetColumn> result = new ArrayList<>();
        int index = 0;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String stringCellValue = cell.getStringCellValue();
            if (StringUtils.isEmpty(stringCellValue)) {
                continue;
            }
            SpreadsheetColumn spreadsheetColumn = new SpreadsheetColumn();
            spreadsheetColumn.setName(stringCellValue);
            spreadsheetColumn.setIndex(index++);

            List<Cell> cellsForColumn = getCellsForColumn(sheet, cell.getColumnIndex());
            ColumnType type = chooseColumnType(cellsForColumn);
            spreadsheetColumn.setType(type);
            result.add(spreadsheetColumn);
        }
        return result;
    }

    private List<Cell> getCellsForColumn(Sheet sheet, int columnIndex) {
        List<Cell> cells = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {
            Row next = rowIterator.next();
            cells.add(next.getCell(columnIndex));
        }
        return cells;
    }

    private ColumnType chooseColumnType(List<Cell> cells) {
        boolean isNominal = false;
        for (Cell cell : cells) {
            if (cell != null && CellType.STRING.equals(cell.getCellTypeEnum())) {
                isNominal = true;
            }
        }
        return isNominal ? ColumnType.NOMINAL : ColumnType.CONTINUOUS;
    }

    private SpreadsheetData fillRows(Sheet sheet, SpreadsheetData spreadsheetData) {
        Iterator<Row> rowIterator = sheet.rowIterator();
        //Miss column name's row
        rowIterator.next();

        List<SpreadsheetRow> rows = new ArrayList<>();
        int rowIndex = 0;
        while (rowIterator.hasNext()) {
            Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
            int columnIndex = 0;
            Map<String, Object> dataRow = new HashMap<>();
            while (cellIterator.hasNext() && columnIndex < spreadsheetData.getColumns().size()) {
                Cell cell = cellIterator.next();
                CellType cellTypeEnum = cell.getCellTypeEnum();
                String key = spreadsheetData.getColumns().get(columnIndex++).getName();
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
            rows.add(new SpreadsheetRow(rowIndex++, dataRow));
        }
        spreadsheetData.setRows(rows);
        return spreadsheetData;
    }

    @Override
    public Optional<Spreadsheet> getActiveSpreadsheetForDoctor(long doctorId) {
        Doctor author = doctorService.findById(doctorId);
        List<Spreadsheet> spreadsheetsByAuthor = spreadsheetRepository.getSpreadsheetsByAuthor(author);
        return spreadsheetsByAuthor.stream().filter(i -> !i.isClosed()).findFirst();
    }

    @Override
    public Optional<Spreadsheet> getActiveSpreadsheetForDoctor(Doctor doctor) {
        List<Spreadsheet> spreadsheetsByAuthor = spreadsheetRepository.getSpreadsheetsByAuthor(doctor);
        return spreadsheetsByAuthor.stream().filter(i -> !i.isClosed()).findFirst();
    }

    @Override
    public Optional<Spreadsheet> getActiveSpreadsheetForCurrentDoctor() {
        return getActiveSpreadsheetForDoctor(doctorService.getCurrentDoctor());
    }

    @Override
    public Spreadsheet createSpreadsheet(MultipartFile excelFile) throws StorageException {
        Optional<Spreadsheet> activeSpreadsheetForCurrentDoctor1 = getActiveSpreadsheetForCurrentDoctor();
        activeSpreadsheetForCurrentDoctor1.ifPresent(spreadsheet -> {
            spreadsheet.setClosed(true);
            spreadsheet.setLastUpdate(new Date());
            spreadsheetRepository.save(spreadsheet);
        });

        Spreadsheet spreadsheet = new Spreadsheet();
        try {
            spreadsheet.setClosed(false);
            SpreadsheetData spreadsheetData = getSpreadsheetData(excelFile);
            spreadsheetData.setSpreadsheet(spreadsheet);
            spreadsheet.setSpreadsheetData(spreadsheetData);
            spreadsheet.setAuthor(doctorService.getCurrentDoctor());
            spreadsheetRepository.save(spreadsheet);
        } catch (StorageException e) {
            System.err.println(e.getMessage());
        }
        return spreadsheet;
    }

    @Override
    public void updateSpreadsheet(Spreadsheet spreadsheet) {
        spreadsheetRepository.save(spreadsheet);
    }
}
