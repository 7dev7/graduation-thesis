package com.dev.domain.converter;

import com.dev.domain.model.DTO.NetworkModelDTO;
import com.dev.domain.model.NetworkModel;

public class NetworkModelDTOConverter {

    public static NetworkModelDTO convert(NetworkModel networkModel) {
        NetworkModelDTO modelDTO = new NetworkModelDTO();
        modelDTO.setId(networkModel.getId());
        modelDTO.setName(networkModel.getName());
        modelDTO.setDateOfCreation(networkModel.getDateOfCreation());
        modelDTO.setDescription(networkModel.getDescription());
        modelDTO.setInputColumns(networkModel.getInputColumns());
        modelDTO.setOutColumns(networkModel.getOutColumns());
        modelDTO.setError(networkModel.getError());
        modelDTO.setPerceptronModel(networkModel.isPerceptronModel());

        if (networkModel.isPerceptronModel()) {
            modelDTO.setHiddenActivationFunction(networkModel.getPerceptron().getHiddenActivationFunc());
            modelDTO.setOutActivationFunction(networkModel.getPerceptron().getOutActivationFunc());
            modelDTO.setHiddenFuncFormatted(ActivationFunctionFormatterConverter.convert(networkModel.getPerceptron().getHiddenActivationFunc()));
            modelDTO.setOutFuncFormatted(ActivationFunctionFormatterConverter.convert(networkModel.getPerceptron().getOutActivationFunc()));
        }
        return modelDTO;
    }
}
