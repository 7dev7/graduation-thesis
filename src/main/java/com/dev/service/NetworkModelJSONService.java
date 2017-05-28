package com.dev.service;

import com.dev.domain.model.NetworkModel;
import com.dev.service.exception.ModelParsingException;

public interface NetworkModelJSONService {
    String getJSONFromModel(NetworkModel networkModel);

    NetworkModel getModelFromJSON(String JSONModel) throws ModelParsingException;
}
