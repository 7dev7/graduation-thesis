package com.dev.service;

import com.dev.domain.model.DTO.ComputeModelDataDTO;
import com.dev.domain.model.DTO.NetworkModelDTO;
import com.dev.domain.model.NetworkModel;

import java.util.List;

public interface NetworkModelService {

    void save(NetworkModel networkModel);

    void update(NetworkModelDTO modelDTO);

    List<NetworkModel> getModelsForCurrentDoctor();

    void removeModelById(long id);

    NetworkModel findById(long id);

    void compute(ComputeModelDataDTO computeModelDataDTO);
}
