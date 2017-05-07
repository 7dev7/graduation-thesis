package com.dev.domain.converter;

import com.dev.domain.model.DTO.SpreadsheetDataDTO;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.domain.model.spreadsheet.SpreadsheetRow;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class SpreadsheetDataDTOConverter {

    public static SpreadsheetDataDTO convert(SpreadsheetData spreadsheetData) {
        SpreadsheetDataDTO spreadsheetDataDTO = new SpreadsheetDataDTO();
        spreadsheetDataDTO.setNumOfRecords(spreadsheetData.getNumOfRecords());
        spreadsheetDataDTO.setColumns(spreadsheetData.getColumnNames());
        spreadsheetDataDTO.setColumnTypes(spreadsheetData.getColumnTypesIndexes());

        JSONArray rows = new JSONArray();
        for (SpreadsheetRow row : spreadsheetData.getRows()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(row.getElements());
            jsonObject.put("___#$RowId$#___", row.getIndex());
            rows.add(jsonObject);
        }
        spreadsheetDataDTO.setRows(rows);
        return spreadsheetDataDTO;
    }
}
