package com.dev.service.train;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.DTO.UserModelTrainInfoDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.network.Perceptron;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.service.exception.TrainingException;
import org.encog.ml.data.MLDataSet;

import java.util.List;

public interface PerceptronTrainingService {
    NetworkModel train(Perceptron perceptron, MLDataSet dataSet);

    List<NetworkModel> train(AutoModeTrainInfoDTO trainInfoDTO, Spreadsheet spreadsheet) throws TrainingException;

    NetworkModel train(UserModelTrainInfoDTO trainInfoDTO, Spreadsheet spreadsheet) throws TrainingException;
}
