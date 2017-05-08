package com.dev.service.train;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.service.exception.TrainingException;
import org.encog.ml.data.MLDataSet;

public interface TrainingDataService {
    MLDataSet buildDataset(SpreadsheetData spreadsheetData, AutoModeTrainInfoDTO trainInfoDTO) throws TrainingException;
}
