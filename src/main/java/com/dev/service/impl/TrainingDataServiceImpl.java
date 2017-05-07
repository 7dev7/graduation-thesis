package com.dev.service.impl;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.spreadsheet.SpreadsheetColumn;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.service.TrainingDataService;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingDataServiceImpl implements TrainingDataService {
    private final NormalizationService normalizationService;

    @Autowired
    public TrainingDataServiceImpl(NormalizationService normalizationService) {
        this.normalizationService = normalizationService;
    }

    @Override
    public MLDataSet buildDataset(SpreadsheetData spreadsheetData, AutoModeTrainInfoDTO trainInfoDTO) {
        double[][] inputs = buildData(spreadsheetData, trainInfoDTO.getInputContinuousColumnIndexes());
        double[][] outputs = buildData(spreadsheetData, trainInfoDTO.getOutputContinuousColumnIndexes());

        //TODO implement calculation low-high
        double[][] normIn = normalizationService.normalizeData(inputs, 1, 0);
        double[][] normOut = normalizationService.normalizeData(outputs, 100, 0);

        MLDataSet dataSet = new BasicNeuralDataSet(normIn, normOut);
        return dataSet;
    }

    private double[][] buildData(SpreadsheetData spreadsheetData, List<Integer> columnIndexes) {
        List<List<Double>> result = new ArrayList<>();
        for (Integer columnIndex : columnIndexes) {
            SpreadsheetColumn column = spreadsheetData.getColumns().get(columnIndex);
            List<Object> collect = spreadsheetData.getRows().stream().map(i -> i.get(column.getName())).collect(Collectors.toCollection(ArrayList::new));
            result.add(cast(collect));
        }
        return transformToArray(result);
    }

    private List<Double> cast(List<Object> items) {
        List<Double> res = new ArrayList<>();
        for (Object obj : items) {
            try {
                double val;
                if (obj == null) {
                    val = -1;
                } else if (obj instanceof Integer) {
                    val = Double.valueOf((Integer) obj);
                } else if (obj instanceof Double) {
                    val = (Double) obj;
                } else {
                    val = Double.parseDouble((String) obj);
                }
                res.add(val);
            } catch (NumberFormatException e) {

            }
        }
        return res;
    }

    private double[][] transformToArray(List<List<Double>> ins) {
        double[][] result = new double[ins.get(0).size()][];

        for (int i = 0; i < ins.get(0).size(); i++) {
            double[] arr = new double[ins.size()];
            for (int j = 0; j < ins.size(); j++) {
                List<Double> doubles = ins.get(j);
                arr[j] = doubles.get(i);
            }
            result[i] = arr;
        }
        return result;
    }
}
