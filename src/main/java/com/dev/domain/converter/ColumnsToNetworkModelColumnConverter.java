package com.dev.domain.converter;

import com.dev.domain.model.network.NetworkModelColumnDefinition;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.domain.model.spreadsheet.SpreadsheetColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ColumnsToNetworkModelColumnConverter {

    public static List<NetworkModelColumnDefinition> getColumnDefs(List<String> columns, Spreadsheet spreadsheet) {
        List<NetworkModelColumnDefinition> columnDefinitions = new ArrayList<>();
        for (String col : columns) {
            NetworkModelColumnDefinition columnDefinition = new NetworkModelColumnDefinition();
            columnDefinition.setName(col);
            Optional<SpreadsheetColumn> spreadsheetColumnOptional =
                    spreadsheet.getColumns().stream().filter(i -> col.equals(i.getName())).findFirst();
            spreadsheetColumnOptional.ifPresent(c -> {
                columnDefinition.setColumnType(c.getType());
                columnDefinition.setMeasurementType(c.getMeasurementType());
            });
            columnDefinitions.add(columnDefinition);
        }
        return columnDefinitions;
    }
}
