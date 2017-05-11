package com.dev.service.train;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.DTO.TrainDataInfoDTO;
import com.dev.domain.model.DTO.UserModelTrainInfoDTO;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.service.exception.TrainingException;

public interface TrainingDataService {
    TrainDataInfoDTO buildDataset(SpreadsheetData spreadsheetData, AutoModeTrainInfoDTO trainInfoDTO) throws TrainingException;

    TrainDataInfoDTO buildDataset(SpreadsheetData spreadsheetData, UserModelTrainInfoDTO trainInfoDTO) throws TrainingException;
}
