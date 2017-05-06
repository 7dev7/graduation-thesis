package com.dev.domain.converter;

import com.dev.domain.model.DTO.SpreadsheetDataDTO;
import com.dev.domain.model.SpreadsheetData;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.Map;

public class SpreadsheetDataDTOConverter {

    public static SpreadsheetDataDTO convert(SpreadsheetData spreadsheetData) {
        SpreadsheetDataDTO spreadsheetDataDTO = new SpreadsheetDataDTO();
        spreadsheetDataDTO.setNumOfRecords(spreadsheetData.getNumOfRecords());
        spreadsheetDataDTO.setColumns(spreadsheetData.getColumns());

        JSONArray rows = new JSONArray();
        for (Map<String, Object> dataRow : spreadsheetData.getRows()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(dataRow);
            rows.add(jsonObject);
        }
        spreadsheetDataDTO.setRows(rows);
        return spreadsheetDataDTO;
    }
}
