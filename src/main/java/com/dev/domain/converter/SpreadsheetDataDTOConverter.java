package com.dev.domain.converter;

import com.dev.domain.model.DTO.SpreadsheetDataDTO;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.Map;

public class SpreadsheetDataDTOConverter {

    public static SpreadsheetDataDTO convert(SpreadsheetData spreadsheetData) {
        SpreadsheetDataDTO spreadsheetDataDTO = new SpreadsheetDataDTO();
        spreadsheetDataDTO.setNumOfRecords(spreadsheetData.getNumOfRecords());
        spreadsheetDataDTO.setColumns(spreadsheetData.getColumns());

        JSONArray rows = new JSONArray();
        int index = 0;
        for (Map<String, Object> dataRow : spreadsheetData.getRows()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(dataRow);
            jsonObject.put("___#$RowId$#___", index++);
            rows.add(jsonObject);
        }
        spreadsheetDataDTO.setRows(rows);
        return spreadsheetDataDTO;
    }
}
