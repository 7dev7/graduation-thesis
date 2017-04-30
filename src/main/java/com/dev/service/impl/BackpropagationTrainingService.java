package com.dev.service.impl;

import com.dev.domain.neuralnetwork.perceptron.Perceptron;
import com.dev.service.PerceptronTrainingService;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.springframework.stereotype.Service;

@Service
public class BackpropagationTrainingService implements PerceptronTrainingService {

    @Override
    public double train(Perceptron perceptron, MLDataSet dataSet) {
        Backpropagation backpropagation = new Backpropagation(perceptron.getNetwork(), dataSet);
        for (int i = 0; i < 50_000; i++) {
            backpropagation.iteration();
        }
        return backpropagation.getError();
    }
}
