package com.dev.service.train.impl;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.DTO.UserModelTrainInfoDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.network.RadialBasisFunctionsNetwork;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.service.exception.TrainingException;
import com.dev.service.train.RBFTrainingService;
import com.dev.service.train.TrainingDataService;
import org.apache.log4j.Logger;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.rbf.training.SVDTraining;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SVDTrainingService implements RBFTrainingService {
    private static final Logger LOGGER = Logger.getLogger(SVDTrainingService.class);
    private static final int NUM_OF_ITERATIONS = 500;
    private final TrainingDataService trainingDataService;

    @Autowired
    public SVDTrainingService(TrainingDataService trainingDataService) {
        this.trainingDataService = trainingDataService;
    }

    @Override
    public NetworkModel train(RadialBasisFunctionsNetwork rbfNetwork, MLDataSet dataSet) {
        SVDTraining svdTraining = new SVDTraining(rbfNetwork.getNetwork(), dataSet);
        LOGGER.info("--> RBF Train executed: " + rbfNetwork);

        for (int i = 0; i < NUM_OF_ITERATIONS; i++) {
            svdTraining.iteration();
        }
        NetworkModel networkModel = new NetworkModel();
        networkModel.setError(svdTraining.getError());
        networkModel.setRbfNetwork(rbfNetwork);
        networkModel.setPerceptronModel(false);
        networkModel.setName("RBF: " + rbfNetwork.getInputNeurons() + " - " + rbfNetwork.getHiddenNeurons() + " - " + rbfNetwork.getOutNeurons());
        return networkModel;
    }

    @Override
    public List<NetworkModel> train(AutoModeTrainInfoDTO trainInfoDTO, SpreadsheetData spreadsheetData) throws TrainingException {
        Integer minNumOfNeuron = trainInfoDTO.getMlpMinNumOfNeuron();
        Integer maxNumOfNeuron = trainInfoDTO.getMlpMaxNumOfNeuron();

        int inputNeurons = trainInfoDTO.getInputContinuousColumnIndexes().size();
        int outNeurons = trainInfoDTO.getOutputContinuousColumnIndexes().size();

        List<NetworkModel> models = new ArrayList<>();
        for (int i = minNumOfNeuron; i <= maxNumOfNeuron; i++) {
            RadialBasisFunctionsNetwork rbfNetwork = new RadialBasisFunctionsNetwork(inputNeurons, i, outNeurons);
            NetworkModel model = train(rbfNetwork, trainingDataService.buildDataset(spreadsheetData, trainInfoDTO));
            models.add(model);
        }
        return models;
    }

    @Override
    public NetworkModel train(UserModelTrainInfoDTO trainInfoDTO, SpreadsheetData spreadsheetData) throws TrainingException {
        int inputNeurons = trainInfoDTO.getInputContinuousColumnIndexes().size();
        int hiddenNurons = trainInfoDTO.getNumOfNeurons();
        int outNeurons = trainInfoDTO.getOutputContinuousColumnIndexes().size();

        RadialBasisFunctionsNetwork rbfNetwork = new RadialBasisFunctionsNetwork(inputNeurons, hiddenNurons, outNeurons);
        return train(rbfNetwork, trainingDataService.buildDataset(spreadsheetData, trainInfoDTO));
    }
}
