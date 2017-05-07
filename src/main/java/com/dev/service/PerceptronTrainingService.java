package com.dev.service;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.TrainedNetworkInfo;
import com.dev.domain.neuralnetwork.perceptron.Perceptron;
import org.encog.ml.data.MLDataSet;

import java.util.List;

public interface PerceptronTrainingService {
    TrainedNetworkInfo train(Perceptron perceptron, MLDataSet dataSet);

    List<TrainedNetworkInfo> train(AutoModeTrainInfoDTO trainInfoDTO);
}
