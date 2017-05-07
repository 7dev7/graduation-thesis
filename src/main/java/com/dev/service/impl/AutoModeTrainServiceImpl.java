package com.dev.service.impl;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.service.AutoModeTrainService;
import com.dev.service.PerceptronTrainingService;
import com.dev.service.exception.TrainingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AutoModeTrainServiceImpl implements AutoModeTrainService {

    private final PerceptronTrainingService perceptronTrainingService;

    @Autowired
    public AutoModeTrainServiceImpl(PerceptronTrainingService perceptronTrainingService) {
        this.perceptronTrainingService = perceptronTrainingService;
    }

    @Override
    public List<NetworkModel> train(AutoModeTrainInfoDTO trainInfoDTO, SpreadsheetData spreadsheetData) throws TrainingException {
        if (trainInfoDTO.getIsMLPNeeded()) {
            return perceptronTrainingService.train(trainInfoDTO, spreadsheetData);
        }
        return null;
    }
}
