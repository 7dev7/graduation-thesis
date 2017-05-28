package com.dev.service.train.impl;

import com.dev.domain.model.DTO.UserModelTrainInfoDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.service.exception.TrainingException;
import com.dev.service.train.PerceptronTrainingService;
import com.dev.service.train.RBFTrainingService;
import com.dev.service.train.UserModelTrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserModelTrainServiceImpl implements UserModelTrainService {
    private final PerceptronTrainingService perceptronTrainingService;
    private final RBFTrainingService rbfTrainingService;

    @Autowired
    public UserModelTrainServiceImpl(PerceptronTrainingService perceptronTrainingService, RBFTrainingService rbfTrainingService) {
        this.perceptronTrainingService = perceptronTrainingService;
        this.rbfTrainingService = rbfTrainingService;
    }

    @Override
    public NetworkModel train(UserModelTrainInfoDTO trainInfoDTO, Spreadsheet spreadsheet) throws TrainingException {
        NetworkModel model;
        if (trainInfoDTO.getIsMLPModel()) {
            model = perceptronTrainingService.train(trainInfoDTO, spreadsheet);
        } else {
            model = rbfTrainingService.train(trainInfoDTO, spreadsheet);
        }
        return model;
    }
}
