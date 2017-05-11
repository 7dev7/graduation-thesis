package com.dev.service.impl;

import com.dev.domain.dao.SpreadsheetRepository;
import com.dev.domain.model.DTO.ValidateInputsDTO;
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
        List<Spreadsheet> spreadsheetsByAuthor = spreadsheetRepository.getSpreadsheetsByOwner(author);
        return spreadsheetsByAuthor.stream().filter(i -> !i.isClosed()).findFirst();
    }

    @Override
    public Optional<Spreadsheet> getActiveSpreadsheetForDoctor(Doctor doctor) {
        List<Spreadsheet> spreadsheetsByAuthor = spreadsheetRepository.getSpreadsheetsByOwner(doctor);
        return spreadsheetsByAuthor.stream().filter(i -> !i.isClosed()).findFirst();
    }

    @Override
    public Optional<Spreadsheet> getActiveSpreadsheetForCurrentDoctor() {
        return getActiveSpreadsheetForDoctor(doctorService.getCurrentDoctor());
    }

    @Override
    public Spreadsheet createSpreadsheet(MultipartFile excelFile) throws StorageException {
        closeActiveSpreadsheet();

        Spreadsheet spreadsheet = new Spreadsheet();
        try {
            spreadsheet.setClosed(false);
            SpreadsheetData spreadsheetData = getSpreadsheetData(excelFile);
            spreadsheet.setSpreadsheetData(spreadsheetData);
            spreadsheet.setOwner(doctorService.getCurrentDoctor());
            spreadsheetRepository.save(spreadsheet);
        } catch (StorageException e) {
            System.err.println(e.getMessage());
        }
        return spreadsheet;
    }

    public Spreadsheet createSpreadsheet() {
        closeActiveSpreadsheet();

        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.setClosed(false);
        SpreadsheetData spreadsheetData = new SpreadsheetData();
        spreadsheet.setSpreadsheetData(spreadsheetData);
        spreadsheet.setOwner(doctorService.getCurrentDoctor());
        spreadsheetRepository.save(spreadsheet);
        return spreadsheet;
    }

    private void closeActiveSpreadsheet() {
        Optional<Spreadsheet> activeSpreadsheetForCurrentDoctor1 = getActiveSpreadsheetForCurrentDoctor();
        activeSpreadsheetForCurrentDoctor1.ifPresent(spreadsheet -> {
            spreadsheet.setClosed(true);
            spreadsheet.setLastUpdate(new Date());
            spreadsheetRepository.save(spreadsheet);
        });
    }

    @Override
    public void updateSpreadsheet(Spreadsheet spreadsheet) {
        spreadsheetRepository.save(spreadsheet);
    }

    @Override
    public void removeColumnByIndex(int index, String initName) throws StorageException {
        Optional<Spreadsheet> spreadsheetOptional = getActiveSpreadsheetForCurrentDoctor();
        Spreadsheet spreadsheet = spreadsheetOptional.orElseGet(this::createSpreadsheet);
        SpreadsheetData spreadsheetData = spreadsheet.getSpreadsheetData();

        Optional<SpreadsheetColumn> columnOptional = spreadsheetData.getColumns().stream().filter(i -> i.getName().equals(initName)).findFirst();
        SpreadsheetColumn spreadsheetColumn = columnOptional.orElseThrow(StorageException::new);

        for (SpreadsheetRow row : spreadsheetData.getRows()) {
            Map<String, Object> elements = row.getElements();
            elements.remove(spreadsheetColumn.getName());
        }
        spreadsheetData.getColumns().remove(spreadsheetColumn);
        updateSpreadsheet(spreadsheet);
    }

    @Override
    public void updateColumn(int index, String initName, String name, ColumnType type) throws StorageException {
        Optional<Spreadsheet> spreadsheetOptional = getActiveSpreadsheetForCurrentDoctor();
        Spreadsheet spreadsheet = spreadsheetOptional.orElseGet(this::createSpreadsheet);
        SpreadsheetData data = spreadsheet.getSpreadsheetData();

        Optional<SpreadsheetColumn> columnOptional = data.getColumns().stream().filter(i -> i.getName().equals(initName)).findFirst();
        SpreadsheetColumn column = columnOptional.orElseThrow(StorageException::new);

        for (SpreadsheetRow row : data.getRows()) {
            Map<String, Object> elements = row.getElements();
            Object val = elements.get(column.getName());
            elements.remove(column.getName());
            elements.put(name, val);
        }
        data.getColumns().remove(column);
        data.getColumns().add(new SpreadsheetColumn(index, name, type));

        updateSpreadsheet(spreadsheet);
    }

    @Override
    public boolean validate(ValidateInputsDTO validateInputsDTO) {
        Optional<Spreadsheet> spreadsheetOptional = getActiveSpreadsheetForCurrentDoctor();
        Spreadsheet spreadsheet = spreadsheetOptional.orElseGet(this::createSpreadsheet);
        SpreadsheetData data = spreadsheet.getSpreadsheetData();

        List<Integer> columnIndexes = validateInputsDTO.getColumnIndexes();

        for (Integer column : columnIndexes) {
            SpreadsheetColumn spreadsheetColumn = data.getColumns().get(column);
            List<SpreadsheetRow> rows = data.getRows();
            for (SpreadsheetRow row : rows) {
                Object o = row.getElements().get(spreadsheetColumn.getName());
                try {
                    if (o == null) {
                        return false;
                    } else if (o instanceof Integer) {
                        double val = Double.valueOf((Integer) o);
                    } else if (o instanceof Double) {
                        double val = (Double) o;
                    } else {
                        double val = Double.parseDouble((String) o);
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return true;
    }
}
