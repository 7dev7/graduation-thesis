package com.dev.service.impl;

import com.dev.domain.model.ActivationFunction;
import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.perceptron.Perceptron;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.service.PerceptronTrainingService;
import com.dev.service.TrainingDataService;
import com.dev.service.exception.TrainingException;
import org.apache.log4j.Logger;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BackpropagationTrainingService implements PerceptronTrainingService {
    private static final Logger LOGGER = Logger.getLogger(BackpropagationTrainingService.class);
    private static final int NUM_OF_ITERATIONS = 10_000;
    private final TrainingDataService trainingDataService;

    @Autowired
    public BackpropagationTrainingService(TrainingDataService trainingDataService) {
        this.trainingDataService = trainingDataService;
    }

    @Override
    public NetworkModel train(Perceptron perceptron, MLDataSet dataSet) {
        Backpropagation backpropagation = new Backpropagation(perceptron.getNetwork(), dataSet);
        LOGGER.info("--> Train executed: " + perceptron);

        for (int i = 0; i < NUM_OF_ITERATIONS; i++) {
            backpropagation.iteration();
        }
        NetworkModel networkModel = new NetworkModel();
        networkModel.setError(backpropagation.getError());
        networkModel.setPerceptron(perceptron);
        networkModel.setHiddenActivationFunction(perceptron.getHiddenActivationFunc());
        networkModel.setOutActivationFunction(perceptron.getOutActivationFunc());
        networkModel.setName("MLP: " + perceptron.getInputNeurons() + " - " + perceptron.getHiddenNeurons() + " - " + perceptron.getOutNeurons());
        return networkModel;
    }

    @Override
    public List<NetworkModel> train(AutoModeTrainInfoDTO trainInfoDTO, SpreadsheetData spreadsheetData) throws TrainingException {
        Integer minNumOfNeuron = trainInfoDTO.getMlpMinNumOfNeuron();
        Integer maxNumOfNeuron = trainInfoDTO.getMlpMaxNumOfNeuron();

        int inputNeurons = trainInfoDTO.getInputContinuousColumnIndexes().size();
        int outNeurons = trainInfoDTO.getOutputContinuousColumnIndexes().size();

        List<ActivationFunction> hiddenNeuronsFuncs = trainInfoDTO.getHiddenNeuronsFuncs();
        List<ActivationFunction> outNeuronsFuncs = trainInfoDTO.getOutNeuronsFuncs();

        List<NetworkModel> infos = new ArrayList<>();
        for (int i = minNumOfNeuron; i <= maxNumOfNeuron; i++) {
            for (ActivationFunction hiddenActivation : hiddenNeuronsFuncs) {
                for (ActivationFunction outActivation : outNeuronsFuncs) {
                    Perceptron perceptron = new Perceptron(inputNeurons, i, outNeurons, hiddenActivation, outActivation);
                    NetworkModel trainInfo = train(perceptron, trainingDataService.buildDataset(spreadsheetData, trainInfoDTO));
                    infos.add(trainInfo);
                }
            }
        }
        return infos;
    }
}
