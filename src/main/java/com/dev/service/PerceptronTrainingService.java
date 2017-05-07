package com.dev.service;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.TrainedNetworkInfo;
import com.dev.domain.model.perceptron.Perceptron;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.service.exception.TrainingException;
import org.encog.ml.data.MLDataSet;

import java.util.List;

public interface PerceptronTrainingService {
    TrainedNetworkInfo train(Perceptron perceptron, MLDataSet dataSet);

    List<TrainedNetworkInfo> train(AutoModeTrainInfoDTO trainInfoDTO, SpreadsheetData spreadsheetData) throws TrainingException;
}
