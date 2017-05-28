package com.dev.service.train;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.DTO.TrainDataInfoDTO;
import com.dev.domain.model.DTO.UserModelTrainInfoDTO;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.service.exception.TrainingException;

public interface TrainingDataService {
    TrainDataInfoDTO buildDataset(Spreadsheet spreadsheet, AutoModeTrainInfoDTO trainInfoDTO) throws TrainingException;

    TrainDataInfoDTO buildDataset(Spreadsheet spreadsheet, UserModelTrainInfoDTO trainInfoDTO) throws TrainingException;
}
