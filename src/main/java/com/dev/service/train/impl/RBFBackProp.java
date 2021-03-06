package com.dev.service.train.impl;

import com.dev.domain.converter.ColumnsToNetworkModelColumnConverter;
import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.DTO.TrainDataInfoDTO;
import com.dev.domain.model.DTO.UserModelTrainInfoDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.network.NetworkModelColumnDefinition;
import com.dev.domain.model.network.RadialBasisFunctionsNetwork;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.service.exception.TrainingException;
import com.dev.service.train.RBFTrainingService;
import com.dev.service.train.TrainingDataService;
import org.apache.log4j.Logger;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RBFBackProp implements RBFTrainingService {
    private static final Logger LOGGER = Logger.getLogger(RBFBackProp.class);
    private static final int NUM_OF_ITERATIONS = 500;
    private final TrainingDataService trainingDataService;

    @Autowired
    public RBFBackProp(TrainingDataService trainingDataService) {
        this.trainingDataService = trainingDataService;
    }

    @Override
    public NetworkModel train(RadialBasisFunctionsNetwork rbfNetwork, MLDataSet dataSet) {
        Backpropagation backpropagation = new Backpropagation(rbfNetwork.getNetwork(), dataSet);
        LOGGER.info("--> RBF Train executed: " + rbfNetwork);

        for (int i = 0; i < NUM_OF_ITERATIONS; i++) {
            backpropagation.iteration();
        }
        NetworkModel networkModel = new NetworkModel();
        networkModel.setError(backpropagation.getError());
        networkModel.setRbfNetwork(rbfNetwork);
        networkModel.setDescription("Сеть радиально-базисных функций с " + rbfNetwork.getHiddenNeurons() + " скрытыми нейронами");
        networkModel.setPerceptronModel(false);
        networkModel.setName("RBF: " + rbfNetwork.getInputNeurons() + " - " + rbfNetwork.getHiddenNeurons() + " - " + rbfNetwork.getOutNeurons());
        return networkModel;
    }

    @Override
    public List<NetworkModel> train(AutoModeTrainInfoDTO trainInfoDTO, Spreadsheet spreadsheet) throws TrainingException {
        Integer minNumOfNeuron = trainInfoDTO.getRbfMinNumOfNeuron();
        Integer maxNumOfNeuron = trainInfoDTO.getRbfMaxNumOfNeuron();

        int inputNeurons = trainInfoDTO.getInputContinuousColumnIndexes().size();
        int outNeurons = trainInfoDTO.getOutputContinuousColumnIndexes().size();

        List<NetworkModel> models = new ArrayList<>();
        for (int i = minNumOfNeuron; i <= maxNumOfNeuron; i++) {
            RadialBasisFunctionsNetwork rbfNetwork = new RadialBasisFunctionsNetwork(inputNeurons, i, outNeurons);
            TrainDataInfoDTO dataInfoDTO = trainingDataService.buildDataset(spreadsheet, trainInfoDTO);

            NetworkModel model = train(rbfNetwork, dataInfoDTO.getMlDataSet());

            List<NetworkModelColumnDefinition> inColumnDefs =
                    ColumnsToNetworkModelColumnConverter.getColumnDefs(dataInfoDTO.getInputColumns(), spreadsheet);
            inColumnDefs.stream().forEach(col -> col.setInNetworkModel(model));
            model.setInputColumns(inColumnDefs);

            List<NetworkModelColumnDefinition> outColumnDefs =
                    ColumnsToNetworkModelColumnConverter.getColumnDefs(dataInfoDTO.getOutColumns(), spreadsheet);
            outColumnDefs.stream().forEach(col -> col.setOutNetworkModel(model));
            model.setOutColumns(outColumnDefs);

            model.setMinIns(dataInfoDTO.getMinIns());
            model.setMaxIns(dataInfoDTO.getMaxIns());
            model.setMinOuts(dataInfoDTO.getMinOuts());
            model.setMaxOuts(dataInfoDTO.getMaxOuts());

            models.add(model);
        }
        return models;
    }

    @Override
    public NetworkModel train(UserModelTrainInfoDTO trainInfoDTO, Spreadsheet spreadsheet) throws TrainingException {
        int inputNeurons = trainInfoDTO.getInputContinuousColumnIndexes().size();
        int hiddenNurons = trainInfoDTO.getNumOfNeurons();
        int outNeurons = trainInfoDTO.getOutputContinuousColumnIndexes().size();

        RadialBasisFunctionsNetwork rbfNetwork = new RadialBasisFunctionsNetwork(inputNeurons, hiddenNurons, outNeurons);
        TrainDataInfoDTO dataInfoDTO = trainingDataService.buildDataset(spreadsheet, trainInfoDTO);
        NetworkModel model = train(rbfNetwork, dataInfoDTO.getMlDataSet());

        List<NetworkModelColumnDefinition> inColumnDefs =
                ColumnsToNetworkModelColumnConverter.getColumnDefs(dataInfoDTO.getInputColumns(), spreadsheet);
        inColumnDefs.stream().forEach(col -> col.setInNetworkModel(model));
        model.setInputColumns(inColumnDefs);

        List<NetworkModelColumnDefinition> outColumnDefs =
                ColumnsToNetworkModelColumnConverter.getColumnDefs(dataInfoDTO.getOutColumns(), spreadsheet);
        outColumnDefs.stream().forEach(col -> col.setOutNetworkModel(model));
        model.setOutColumns(outColumnDefs);

        model.setMinIns(dataInfoDTO.getMinIns());
        model.setMaxIns(dataInfoDTO.getMaxIns());
        model.setMinOuts(dataInfoDTO.getMinOuts());
        model.setMaxOuts(dataInfoDTO.getMaxOuts());

        return model;
    }
}
