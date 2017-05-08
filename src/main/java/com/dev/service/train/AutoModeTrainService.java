package com.dev.service.train;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.spreadsheet.SpreadsheetData;
import com.dev.service.exception.TrainingException;

import java.util.List;

public interface AutoModeTrainService {
    List<NetworkModel> train(AutoModeTrainInfoDTO trainInfoDTO, SpreadsheetData spreadsheetData) throws TrainingException;
}
