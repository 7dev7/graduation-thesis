package com.dev.domain.converter;

import com.dev.domain.model.DTO.SpreadsheetDataDTO;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.domain.model.spreadsheet.SpreadsheetRow;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class SpreadsheetDataDTOConverter {

    public static SpreadsheetDataDTO convert(Spreadsheet spreadsheet) {
        SpreadsheetDataDTO spreadsheetDataDTO = new SpreadsheetDataDTO();
        spreadsheetDataDTO.setNumOfRecords(spreadsheet.getNumOfRecords());
        spreadsheetDataDTO.setColumns(spreadsheet.getColumnNames());
        spreadsheetDataDTO.setColumnTypes(spreadsheet.getColumnTypesIndexes());

        JSONArray rows = new JSONArray();
        for (SpreadsheetRow row : spreadsheet.getRows()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(row.getCellsMap());
            jsonObject.put("___#$RowId$#___", row.getIndex());
            rows.add(jsonObject);
        }
        spreadsheetDataDTO.setRows(rows);
        return spreadsheetDataDTO;
    }
}
