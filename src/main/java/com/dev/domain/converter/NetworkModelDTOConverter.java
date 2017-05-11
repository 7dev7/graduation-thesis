package com.dev.domain.converter;

import com.dev.domain.model.DTO.NetworkModelDTO;
import com.dev.domain.model.NetworkModel;

public class NetworkModelDTOConverter {

    public static NetworkModelDTO convert(NetworkModel networkModel) {
        NetworkModelDTO modelDTO = new NetworkModelDTO();
        modelDTO.setId(networkModel.getId());
        modelDTO.setError(networkModel.getError());
        modelDTO.setHiddenActivationFunction(networkModel.getHiddenActivationFunction());
        modelDTO.setOutActivationFunction(networkModel.getOutActivationFunction());
        modelDTO.setName(networkModel.getName());
        modelDTO.setDateOfCreation(networkModel.getDateOfCreation());
        modelDTO.setDescription(networkModel.getDescription());
        modelDTO.setPerceptronModel(networkModel.isPerceptronModel());
        modelDTO.setHiddenFuncFormatted(ActivationFunctionFormatterConverter.convert(networkModel.getHiddenActivationFunction()));
        modelDTO.setOutFuncFormatted(ActivationFunctionFormatterConverter.convert(networkModel.getOutActivationFunction()));
        return modelDTO;
    }
}
