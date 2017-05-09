package com.dev.service;

import com.dev.domain.model.NetworkModel;

import java.util.List;

public interface NetworkModelService {

    void save(NetworkModel networkModel);

    List<NetworkModel> getModelsForCurrentDoctor();

    void removeModelById(long id);
}
