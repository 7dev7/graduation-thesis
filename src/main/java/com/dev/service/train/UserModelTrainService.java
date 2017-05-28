package com.dev.service.train;

import com.dev.domain.model.DTO.UserModelTrainInfoDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.service.exception.TrainingException;

public interface UserModelTrainService {
    NetworkModel train(UserModelTrainInfoDTO trainInfoDTO, Spreadsheet spreadsheet) throws TrainingException;
}
