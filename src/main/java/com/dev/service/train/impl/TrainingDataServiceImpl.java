package com.dev.service.train.impl;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.DTO.TrainDataInfoDTO;
import com.dev.domain.model.DTO.UserModelTrainInfoDTO;
import com.dev.domain.model.spreadsheet.SpreadsheetColumn;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.service.NormalizationService;
import com.dev.service.exception.TrainingException;
import com.dev.service.train.TrainingDataService;
import org.encog.ml.data.folded.FoldedDataSet;
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
    public TrainDataInfoDTO buildDataset(SpreadsheetData spreadsheetData, AutoModeTrainInfoDTO trainInfoDTO) throws TrainingException {
        return build(spreadsheetData, trainInfoDTO.getInputContinuousColumnIndexes(), trainInfoDTO.getOutputContinuousColumnIndexes());
    }

    @Override
    public TrainDataInfoDTO buildDataset(SpreadsheetData spreadsheetData, UserModelTrainInfoDTO trainInfoDTO) throws TrainingException {
        return build(spreadsheetData, trainInfoDTO.getInputContinuousColumnIndexes(), trainInfoDTO.getOutputContinuousColumnIndexes());
    }

    private TrainDataInfoDTO build(SpreadsheetData spreadsheetData, List<Integer> inputContinuousColumnsIndexes,
                                   List<Integer> outputContinuousColumnsIndexes) throws TrainingException {
        double[][] inputs = buildData(spreadsheetData, inputContinuousColumnsIndexes);
        double[][] outputs = buildData(spreadsheetData, outputContinuousColumnsIndexes);

        //TODO add normalization check needed

        List<Double> minIn = getMinValues(inputs);
        List<Double> maxIn = getMaxValues(inputs);
        double[][] normIn = normalize(inputs, minIn, maxIn);


        List<Double> minOut = getMinValues(outputs);
        List<Double> maxOut = getMaxValues(outputs);
        double[][] normOut = normalize(outputs, minOut, maxOut);

        FoldedDataSet mlDataPairs = new FoldedDataSet(new BasicNeuralDataSet(normIn, normOut));
        TrainDataInfoDTO dataInfoDTO = new TrainDataInfoDTO();
        dataInfoDTO.setMlDataSet(mlDataPairs);
        dataInfoDTO.setMinIns(minIn);
        dataInfoDTO.setMaxIns(maxIn);
        dataInfoDTO.setMinOuts(minOut);
        dataInfoDTO.setMaxOuts(maxOut);

        List<String> inCols = new ArrayList<>();
        for (Integer columnIndex : inputContinuousColumnsIndexes) {
            SpreadsheetColumn spreadsheetColumn = spreadsheetData.getColumns().get(columnIndex);
            inCols.add(spreadsheetColumn.getName());
        }
        dataInfoDTO.setInputColumns(inCols);

        List<String> outCols = new ArrayList<>();
        for (Integer columnIndex : outputContinuousColumnsIndexes) {
            SpreadsheetColumn spreadsheetColumn = spreadsheetData.getColumns().get(columnIndex);
            outCols.add(spreadsheetColumn.getName());
        }

        dataInfoDTO.setOutColumns(outCols);

        return dataInfoDTO;
    }

    private double[][] normalize(double[][] data, List<Double> minValues, List<Double> maxValues) {
        double[][] normIn = new double[data.length][];

        for (int i = 0; i < data.length; i++) {
            double[] nArr = new double[data[i].length];
            for (int j = 0; j < data[i].length; j++) {
                double min = minValues.get(j);
                double max = maxValues.get(j);
                double normed = normalizationService.normalizeData(data[i][j], max, min);
                nArr[j] = normed;
            }
            normIn[i] = nArr;
        }
        return normIn;
    }

    private List<Double> getMinValues(double[][] data) {
        List<Double> mins = new ArrayList<>();
        for (int i = 0; i < data[0].length; i++) {
            double min = Double.MAX_VALUE;
            for (int j = 0; j < data.length; j++) {
                min = (min > data[j][i]) ? data[j][i] : min;
            }
            mins.add(min * 1.25);
        }
        return mins;
    }

    private List<Double> getMaxValues(double[][] data) {
        List<Double> maxs = new ArrayList<>();
        for (int i = 0; i < data[0].length; i++) {
            double max = Double.MIN_VALUE;
            for (int j = 0; j < data.length; j++) {
                max = (max < data[j][i]) ? data[j][i] : max;
            }
            maxs.add(max * 1.25);
        }
        return maxs;
    }

    private double[][] buildData(SpreadsheetData spreadsheetData, List<Integer> columnIndexes) throws TrainingException {
        List<List<Double>> result = new ArrayList<>();
        for (Integer columnIndex : columnIndexes) {
            SpreadsheetColumn spreadsheetColumn = spreadsheetData.getColumns().get(columnIndex);
            if (spreadsheetColumn == null) {
                throw new TrainingException("Incorrect column index");
            }
            List<Object> collect = spreadsheetData.getRows().stream()
                    .map(i -> i.getElements().get(spreadsheetColumn.getName()))
                    .collect(Collectors.toCollection(ArrayList::new));
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
                System.err.println(e.getMessage());
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
