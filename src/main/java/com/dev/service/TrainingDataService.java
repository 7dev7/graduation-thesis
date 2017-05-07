package com.dev.service;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import org.encog.ml.data.MLDataSet;

public interface TrainingDataService {
    MLDataSet buildDataset(SpreadsheetData spreadsheetData, AutoModeTrainInfoDTO trainInfoDTO);
}
