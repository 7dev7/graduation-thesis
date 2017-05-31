package com.dev.service.train.impl;

import com.dev.domain.converter.ActivationFunctionFormatterConverter;
import com.dev.domain.converter.ColumnsToNetworkModelColumnConverter;
import com.dev.domain.model.ActivationFunction;
import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.DTO.TrainDataInfoDTO;
import com.dev.domain.model.DTO.UserModelTrainInfoDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.network.NetworkModelColumnDefinition;
import com.dev.domain.model.network.Perceptron;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.service.exception.TrainingException;
import com.dev.service.train.PerceptronTrainingService;
import com.dev.service.train.TrainingDataService;
import org.apache.log4j.Logger;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.training.cross.CrossValidationKFold;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BackpropagationTrainingService implements PerceptronTrainingService {
    private static final Logger LOGGER = Logger.getLogger(BackpropagationTrainingService.class);
    private static final int NUM_OF_ITERATIONS = 500;
    private final TrainingDataService trainingDataService;

    @Autowired
    public BackpropagationTrainingService(TrainingDataService trainingDataService) {
        this.trainingDataService = trainingDataService;
    }

    @Override
    public NetworkModel train(Perceptron perceptron, MLDataSet dataSet) {
        //TODO set learn rate, momentum
        Backpropagation backpropagation = new Backpropagation(perceptron.getNetwork(), dataSet, 0.1, 0.1);
        LOGGER.info("--> Perceptron Train executed: " + perceptron);

        CrossValidationKFold crossValidationKFold = new CrossValidationKFold(backpropagation, 4);

        for (int i = 0; i < NUM_OF_ITERATIONS; i++) {
            crossValidationKFold.iteration();
        }
        NetworkModel networkModel = new NetworkModel();
        networkModel.setError(backpropagation.getError());
        networkModel.setPerceptron(perceptron);
        networkModel.setPerceptronModel(true);
        networkModel.setDescription("Многослойный перcептрон с " + perceptron.getHiddenNeurons() + " скрытыми нейронами");
        networkModel.setName("MLP: " + perceptron.getInputNeurons() + " - " + perceptron.getHiddenNeurons() + " - " + perceptron.getOutNeurons());
        return networkModel;
    }

    @Override
    public List<NetworkModel> train(AutoModeTrainInfoDTO trainInfoDTO, Spreadsheet spreadsheet) throws TrainingException {
        Integer minNumOfNeuron = trainInfoDTO.getMlpMinNumOfNeuron();
        Integer maxNumOfNeuron = trainInfoDTO.getMlpMaxNumOfNeuron();

        int inputNeurons = trainInfoDTO.getInputContinuousColumnIndexes().size();
        int outNeurons = trainInfoDTO.getOutputContinuousColumnIndexes().size();

        List<ActivationFunction> hiddenNeuronsFuncs = trainInfoDTO.getHiddenNeuronsFuncs();
        List<ActivationFunction> outNeuronsFuncs = trainInfoDTO.getOutNeuronsFuncs();

        List<NetworkModel> models = new ArrayList<>();
        for (int i = minNumOfNeuron; i <= maxNumOfNeuron; i++) {
            for (ActivationFunction hiddenActivation : hiddenNeuronsFuncs) {
                for (ActivationFunction outActivation : outNeuronsFuncs) {
                    Perceptron perceptron = new Perceptron(inputNeurons, i, outNeurons, hiddenActivation, outActivation);
                    TrainDataInfoDTO dataInfoDTO = trainingDataService.buildDataset(spreadsheet, trainInfoDTO);

                    perceptron.setHiddenFuncFormatted(ActivationFunctionFormatterConverter.convert(perceptron.getHiddenActivationFunc()));
                    perceptron.setHiddenActivationFunc(perceptron.getHiddenActivationFunc());
                    perceptron.setOutFuncFormatted(ActivationFunctionFormatterConverter.convert(perceptron.getOutActivationFunc()));
                    perceptron.setOutActivationFunc(perceptron.getOutActivationFunc());

                    NetworkModel model = train(perceptron, dataInfoDTO.getMlDataSet());

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
            }
        }
        return models;
    }

    @Override
    public NetworkModel train(UserModelTrainInfoDTO trainInfoDTO, Spreadsheet spreadsheet) throws TrainingException {
        int inputNeurons = trainInfoDTO.getInputContinuousColumnIndexes().size();
        int hiddenNeurons = trainInfoDTO.getNumOfNeurons();
        int outNeurons = trainInfoDTO.getOutputContinuousColumnIndexes().size();
        ActivationFunction hiddenActivationFunction = trainInfoDTO.getHiddenActivationFunction();
        ActivationFunction outActivationFunction = trainInfoDTO.getOutActivationFunction();

        Perceptron perceptron = new Perceptron(inputNeurons, hiddenNeurons, outNeurons, hiddenActivationFunction, outActivationFunction);
        TrainDataInfoDTO dataInfoDTO = trainingDataService.buildDataset(spreadsheet, trainInfoDTO);

        NetworkModel model = train(perceptron, dataInfoDTO.getMlDataSet());

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
