package com.dev.service;

import com.dev.domain.neuralnetwork.perceptron.Perceptron;
import org.encog.ml.data.MLDataSet;

public interface PerceptronTrainingService {
    double train(Perceptron perceptron, MLDataSet dataSet);
}
