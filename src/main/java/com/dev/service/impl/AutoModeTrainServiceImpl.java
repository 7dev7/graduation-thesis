package com.dev.service.impl;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.service.AutoModeTrainService;
import com.dev.service.PerceptronTrainingService;
import com.dev.service.RBFTrainingService;
import com.dev.service.exception.TrainingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AutoModeTrainServiceImpl implements AutoModeTrainService {
    private final PerceptronTrainingService perceptronTrainingService;
    private final RBFTrainingService rbfTrainingService;

    @Autowired
    public AutoModeTrainServiceImpl(PerceptronTrainingService perceptronTrainingService, RBFTrainingService rbfTrainingService) {
        this.perceptronTrainingService = perceptronTrainingService;
        this.rbfTrainingService = rbfTrainingService;
    }

    @Override
    public List<NetworkModel> train(AutoModeTrainInfoDTO trainInfoDTO, SpreadsheetData spreadsheetData) throws TrainingException {
        List<NetworkModel> models = new ArrayList<>();
        if (trainInfoDTO.getIsMLPNeeded()) {
            models.addAll(perceptronTrainingService.train(trainInfoDTO, spreadsheetData));
        }
        if (trainInfoDTO.getIsRBFNeeded()) {
            models.addAll(rbfTrainingService.train(trainInfoDTO, spreadsheetData));
        }
        return models;
    }
}
