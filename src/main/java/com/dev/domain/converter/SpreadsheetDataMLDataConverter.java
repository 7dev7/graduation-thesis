package com.dev.domain.converter;

import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.service.impl.NormalizationService;
import org.encog.ml.data.MLDataSet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SpreadsheetDataMLDataConverter {

    private final NormalizationService normalizationService;

    @Autowired
    public SpreadsheetDataMLDataConverter(NormalizationService normalizationService) {
        this.normalizationService = normalizationService;
    }

    public static MLDataSet convert(SpreadsheetData spreadsheetData, List<Integer> inputColumnIndexes, List<Integer> outputColumnIndexes) {
        MLDataSet dataSet = null;
        return dataSet;
    }
}
