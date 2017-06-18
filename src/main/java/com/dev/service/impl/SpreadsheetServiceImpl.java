package com.dev.service.impl;

import com.dev.domain.model.DTO.ValidateInputsDTO;
import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.model.spreadsheet.*;
import com.dev.domain.repository.SpreadsheetColumnRepository;
import com.dev.domain.repository.SpreadsheetRepository;
import com.dev.domain.repository.SpreadsheetRowRepository;
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
    private final SpreadsheetColumnRepository spreadsheetColumnRepository;
    private final SpreadsheetRowRepository spreadsheetRowRepository;
    private final DoctorService doctorService;

    @Autowired
    public SpreadsheetServiceImpl(DoctorService doctorService, SpreadsheetRepository spreadsheetRepository,
                                  SpreadsheetColumnRepository spreadsheetColumnRepository, SpreadsheetRowRepository spreadsheetRowRepository) {
        this.doctorService = doctorService;
        this.spreadsheetRepository = spreadsheetRepository;
        this.spreadsheetColumnRepository = spreadsheetColumnRepository;
        this.spreadsheetRowRepository = spreadsheetRowRepository;
    }

    public Spreadsheet fillSpreadsheet(Spreadsheet spreadsheet, MultipartFile excelFile) throws StorageException {
        try {
            Workbook workbook = WorkbookFactory.create(excelFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            spreadsheet = buildDataWithBasicInfo(spreadsheet, sheet);
            spreadsheet = fillRows(sheet, spreadsheet);
        } catch (IOException | InvalidFormatException e) {
            throw new StorageException(e.getMessage(), e);
        }
        return spreadsheet;
    }

    private Spreadsheet buildDataWithBasicInfo(Spreadsheet spreadsheet, Sheet sheet) {
        spreadsheet.setColumns(buildColumns(spreadsheet, sheet));
        return spreadsheet;
    }

    private List<SpreadsheetColumn> buildColumns(Spreadsheet spreadsheet, Sheet sheet) {
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

            List<Cell> cellsForColumn = getCellsForColumn(sheet, cell.getColumnIndex());
            ColumnType type = chooseColumnType(cellsForColumn);
            MeasurementType measurementType = chooseMeasurementType(cellsForColumn);

            spreadsheetColumn.setName(stringCellValue);
            spreadsheetColumn.setIndex(index++);
            spreadsheetColumn.setType(type);
            spreadsheetColumn.setMeasurementType(measurementType);
            spreadsheetColumn.setSpreadsheet(spreadsheet);
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

    private MeasurementType chooseMeasurementType(List<Cell> cells) {
        for (Cell cell : cells) {
            if (cell != null) {
                if (CellType.STRING.equals(cell.getCellTypeEnum())) {
                    return MeasurementType.TEXT;
                }
            }
        }
        boolean containsDouble = false;
        for (Cell cell : cells) {
            if (cell != null) {
                if (CellType.NUMERIC.equals(cell.getCellTypeEnum())) {
                    if (cell.getNumericCellValue() % 1 != 0) {
                        containsDouble = true;
                    }
                }
            }
        }
        return containsDouble ? MeasurementType.DOUBLE : MeasurementType.INTEGER;
    }

    private Spreadsheet fillRows(Sheet sheet, Spreadsheet spreadsheet) {
        Iterator<Row> rowIterator = sheet.rowIterator();
        //Miss column name's row
        rowIterator.next();

        List<SpreadsheetRow> rows = new ArrayList<>();
        int rowIndex = 0;
        while (rowIterator.hasNext()) {
            Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
            int columnIndex = 0;

            SpreadsheetRow spreadsheetRow = new SpreadsheetRow();
            spreadsheetRow.setIndex(rowIndex++);
            spreadsheetRow.setSpreadsheet(spreadsheet);

            List<SpreadsheetCell> cells = new ArrayList<>();

            while (cellIterator.hasNext() && columnIndex < spreadsheet.getColumns().size()) {
                Cell cell = cellIterator.next();
                CellType cellTypeEnum = cell.getCellTypeEnum();

                SpreadsheetCell spreadsheetCell = new SpreadsheetCell();
                spreadsheetCell.setSpreadsheetColumn(spreadsheet.getColumns().get(columnIndex++));
                spreadsheetCell.setSpreadsheetRow(spreadsheetRow);

                switch (cellTypeEnum) {
                    case STRING:
                        spreadsheetCell.setValue(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        double numericCellValue = cell.getNumericCellValue();
                        if (numericCellValue % 1 == 0) {
                            spreadsheetCell.setValue(String.valueOf((int) numericCellValue));
                        } else {
                            spreadsheetCell.setValue(String.valueOf(numericCellValue));
                        }
                        break;
                }
                cells.add(spreadsheetCell);
            }
            spreadsheetRow.setSpreadsheetCells(cells);
            rows.add(spreadsheetRow);
        }
        spreadsheet.setRows(rows);
        return spreadsheet;
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
            fillSpreadsheet(spreadsheet, excelFile);
            spreadsheet.setOwner(doctorService.getCurrentDoctor());
            spreadsheetRepository.save(spreadsheet);
        } catch (StorageException e) {
            System.err.println(e.getMessage());
        }
        return spreadsheet;
    }

    @Override
    public Spreadsheet createSpreadsheet() {
        closeActiveSpreadsheet();

        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.setClosed(false);
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

        Optional<SpreadsheetColumn> columnOptional = spreadsheet.getColumns().stream().filter(i -> i.getName().equals(initName)).findFirst();
        SpreadsheetColumn spreadsheetColumn = columnOptional.orElseThrow(StorageException::new);

        for (SpreadsheetRow row : spreadsheet.getRows()) {
            Optional<SpreadsheetCell> cell = row.getSpreadsheetCells().stream().filter(i -> initName.equals(i.getSpreadsheetColumn().getName())).findFirst();
            cell.ifPresent(i -> {
                row.getSpreadsheetCells().remove(i);
            });
        }
        spreadsheet.getColumns().remove(spreadsheetColumn);
        spreadsheetColumnRepository.delete(spreadsheetColumn);
        updateSpreadsheet(spreadsheet);
    }

    @Override
    public void updateColumn(int index, String initName, String name, ColumnType type) throws StorageException {
        Optional<Spreadsheet> spreadsheetOptional = getActiveSpreadsheetForCurrentDoctor();
        Spreadsheet spreadsheet = spreadsheetOptional.orElseGet(this::createSpreadsheet);

        Optional<SpreadsheetColumn> columnOptional = spreadsheet.getColumns().stream().filter(i -> i.getName().equals(initName)).findFirst();
        SpreadsheetColumn column = columnOptional.orElseThrow(StorageException::new);
        column.setName(name);

        spreadsheetColumnRepository.save(column);
    }

    @Override
    public int addRow(Map<String, Object> paramsMap) throws StorageException {
        Optional<Spreadsheet> spreadsheetOptional = getActiveSpreadsheetForCurrentDoctor();
        Spreadsheet spreadsheet = spreadsheetOptional.orElseThrow(StorageException::new);

        List<SpreadsheetCell> cells = new ArrayList<>();

        SpreadsheetRow spreadsheetRow = new SpreadsheetRow();

        for (SpreadsheetColumn column : spreadsheet.getColumns()) {
            Object o = paramsMap.get(column.getName());
            if (o != null) {
                SpreadsheetCell cell = new SpreadsheetCell();
                cell.setSpreadsheetRow(spreadsheetRow);
                cell.setSpreadsheetColumn(column);
                cell.setValue(o.toString());
                cells.add(cell);
            }
        }
        spreadsheetRow.setIndex(spreadsheet.getMaxRowIndex() + 1);
        spreadsheetRow.setSpreadsheetCells(cells);
        spreadsheetRow.setSpreadsheet(spreadsheet);

        spreadsheetRowRepository.save(spreadsheetRow);

        spreadsheet.getRows().add(spreadsheetRow);

        updateSpreadsheet(spreadsheet);
        return spreadsheetRow.getIndex();
    }

    @Override
    public void removeRowByIndex(int index) throws StorageException {
        Optional<Spreadsheet> spreadsheetOptional = getActiveSpreadsheetForCurrentDoctor();
        Spreadsheet spreadsheet = spreadsheetOptional.orElseThrow(StorageException::new);

        Optional<SpreadsheetRow> spreadsheetRowOptional = spreadsheet.getRows().stream().filter(i -> i.getIndex() == index).findFirst();
        spreadsheetRowOptional.ifPresent(i -> {
            spreadsheet.getRows().remove(i);
            spreadsheetRowRepository.delete(i);
        });
        updateSpreadsheet(spreadsheet);
    }

    @Override
    public void editRowByIndex(int index, Map<String, Object> paramsMap) throws StorageException {
        Optional<Spreadsheet> spreadsheetOptional = getActiveSpreadsheetForCurrentDoctor();
        Spreadsheet spreadsheet = spreadsheetOptional.orElseThrow(StorageException::new);

        Optional<SpreadsheetRow> spreadsheetRowOptional = spreadsheet.getRows().stream().filter(i -> i.getIndex() == index).findFirst();
        spreadsheetRowOptional.ifPresent(i -> {
            for (SpreadsheetColumn column : spreadsheet.getColumns()) {
                Object o = paramsMap.get(column.getName());
                if (o != null) {
                    SpreadsheetCell cell = i.getCellByColumn(column);
                    if (cell != null) {
                        cell.setValue(o.toString());
                    }
                }
            }
            spreadsheetRowRepository.save(i);
        });
        updateSpreadsheet(spreadsheet);
    }

    @Override
    public boolean validate(ValidateInputsDTO validateInputsDTO) {
        Optional<Spreadsheet> spreadsheetOptional = getActiveSpreadsheetForCurrentDoctor();
        Spreadsheet spreadsheet = spreadsheetOptional.orElseGet(this::createSpreadsheet);

        List<Integer> columnIndexes = validateInputsDTO.getColumnIndexes();

        for (Integer column : columnIndexes) {
            SpreadsheetColumn spreadsheetColumn = spreadsheet.getColumns().get(column);
            List<SpreadsheetRow> rows = spreadsheet.getRows();
            for (SpreadsheetRow row : rows) {
                Object o = row.getCellsMap().get(spreadsheetColumn.getName());
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
