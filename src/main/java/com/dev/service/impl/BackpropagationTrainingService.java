package com.dev.service.impl;

import com.dev.domain.model.ActivationFunction;
import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.TrainedNetworkInfo;
import com.dev.domain.model.perceptron.Perceptron;
import com.dev.service.PerceptronTrainingService;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BackpropagationTrainingService implements PerceptronTrainingService {
    private static final int NUM_OF_ITERATIONS = 50_000;

    @Override
    public TrainedNetworkInfo train(Perceptron perceptron, MLDataSet dataSet) {
        Backpropagation backpropagation = new Backpropagation(perceptron.getNetwork(), dataSet);
        for (int i = 0; i < NUM_OF_ITERATIONS; i++) {
            backpropagation.iteration();
        }
        TrainedNetworkInfo trainedNetworkInfo = new TrainedNetworkInfo();
        trainedNetworkInfo.setError(backpropagation.getError());
        trainedNetworkInfo.setPerceptron(perceptron);
        trainedNetworkInfo.setHiddenActivationFunction(perceptron.getHiddenActivationFunc());
        trainedNetworkInfo.setOutActivationFunction(perceptron.getOutActivationFunc());
        //TODO set name
        return trainedNetworkInfo;
    }

    @Override
    public List<TrainedNetworkInfo> train(AutoModeTrainInfoDTO trainInfoDTO) {
        Integer minNumOfNeuron = trainInfoDTO.getMlpMinNumOfNeuron();
        Integer maxNumOfNeuron = trainInfoDTO.getMlpMaxNumOfNeuron();

        int inputNeurons = trainInfoDTO.getInputContinuousColumnIndexes().size();
        int outNeurons = trainInfoDTO.getOutputContinuousColumnIndexes().size();

        List<ActivationFunction> hiddenNeuronsFuncs = trainInfoDTO.getHiddenNeuronsFuncs();
        List<ActivationFunction> outNeuronsFuncs = trainInfoDTO.getOutNeuronsFuncs();

        List<TrainedNetworkInfo> infos = new ArrayList<>();
        for (int i = minNumOfNeuron; i <= maxNumOfNeuron; i++) {
            for (ActivationFunction hiddenActivation : hiddenNeuronsFuncs) {
                for (ActivationFunction outActivation : outNeuronsFuncs) {
                    Perceptron perceptron = new Perceptron(inputNeurons, i, outNeurons, hiddenActivation, outActivation);
                    TrainedNetworkInfo trainInfo = train(perceptron, null);
                    infos.add(trainInfo);
                }
            }
        }
        return infos;
    }
}
