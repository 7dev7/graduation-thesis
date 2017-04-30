package com.dev.service.impl;

import com.dev.domain.DTO.ExcelInfoDTO;
import com.dev.service.ExcelService;
import com.dev.service.exception.StorageException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public ExcelInfoDTO getFileInfo(MultipartFile excelFile) throws StorageException {
        ExcelInfoDTO excelInfoDTO;
        try {
            Workbook workbook = WorkbookFactory.create(excelFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            excelInfoDTO = buildInfoWithBasicInfo(sheet);
            excelInfoDTO = fillRows(sheet, excelInfoDTO);
        } catch (IOException | InvalidFormatException e) {
            throw new StorageException(e.getMessage(), e);
        }
        return excelInfoDTO;
    }

    private ExcelInfoDTO buildInfoWithBasicInfo(Sheet sheet) {
        ExcelInfoDTO excelInfoDTO = new ExcelInfoDTO();
        excelInfoDTO.setTotal(sheet.getLastRowNum() - 1);
        excelInfoDTO.setRecords(sheet.getLastRowNum() - 1);
        Iterator<Cell> cellIterator = sheet.getRow(0).cellIterator();

        List<String> columns = new ArrayList<>();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            columns.add(cell.getStringCellValue());
        }
        excelInfoDTO.setColumns(columns);
        return excelInfoDTO;
    }

    private ExcelInfoDTO fillRows(Sheet sheet, ExcelInfoDTO excelInfoDTO) {
        Iterator<Row> rowIterator = sheet.rowIterator();
        //Miss column name's row
        rowIterator.next();

        JSONArray rows = new JSONArray();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();
            int index = 0;
            JSONObject jsonObject = new JSONObject();

            while (cellIterator.hasNext() && index < excelInfoDTO.getColumns().size()) {
                Cell cell = cellIterator.next();
                CellType cellTypeEnum = cell.getCellTypeEnum();
                String key = excelInfoDTO.getColumns().get(index++);
                switch (cellTypeEnum) {
                    case STRING:
                        jsonObject.put(key, cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        jsonObject.put(key, String.valueOf(cell.getNumericCellValue()));
                        break;
                }
            }
            rows.add(jsonObject);
        }
        excelInfoDTO.setRows(rows);
        return excelInfoDTO;
    }
}
