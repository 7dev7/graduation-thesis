package com.dev.service.impl;

import com.dev.domain.converter.ActivationFunctionFormatterConverter;
import com.dev.domain.model.ActivationFunction;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.network.Perceptron;
import com.dev.domain.model.network.RadialBasisFunctionsNetwork;
import com.dev.service.DoctorService;
import com.dev.service.NetworkModelJSONService;
import com.dev.service.NetworkModelService;
import com.dev.service.exception.ModelParsingException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.JSONValue;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.rbf.RBFNetwork;
import org.encog.persist.EncogDirectoryPersistence;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NetworkModelJSONServiceImpl implements NetworkModelJSONService {
    private final NetworkModelService networkModelService;
    private final DoctorService doctorService;

    @Autowired
    public NetworkModelJSONServiceImpl(NetworkModelService networkModelService, DoctorService doctorService) {
        this.networkModelService = networkModelService;
        this.doctorService = doctorService;
    }

    @Override
    public String getJSONFromModel(NetworkModel networkModel) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("network_structure", getTextualNetworkStructure(networkModel));
            jsonObject.put("name", networkModel.getName());
            jsonObject.put("description", networkModel.getDescription());
            jsonObject.put("isPerceptronModel", networkModel.isPerceptronModel());
            jsonObject.put("error", networkModel.getError());
            jsonObject.put("input_columns", networkModel.getInputColumns());
            jsonObject.put("out_columns", networkModel.getOutColumns());

            if (networkModel.isPerceptronModel()) {
                jsonObject.put("inNeurons", networkModel.getPerceptron().getInputNeurons());
                jsonObject.put("hiddenNeurons", networkModel.getPerceptron().getHiddenNeurons());
                jsonObject.put("outNeurons", networkModel.getPerceptron().getOutNeurons());

                jsonObject.put("hiddenActivationFunction", networkModel.getPerceptron().getHiddenActivationFunc());
                jsonObject.put("outActivationFunction", networkModel.getPerceptron().getOutActivationFunc());
            } else {
                jsonObject.put("inNeurons", networkModel.getRbfNetwork().getInputNeurons());
                jsonObject.put("hiddenNeurons", networkModel.getRbfNetwork().getHiddenNeurons());
                jsonObject.put("outNeurons", networkModel.getRbfNetwork().getOutNeurons());
            }

            return new org.json.JSONObject(jsonObject.toJSONString(JSONStyle.NO_COMPRESS)).toString(2);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toJSONString(JSONStyle.NO_COMPRESS);
    }

    @Override
    public NetworkModel getModelFromJSON(String JSONModel) throws ModelParsingException {
        NetworkModel networkModel = new NetworkModel();

        try {
            JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(JSONModel);

            String name = jsonObject.getAsString("name");
            String description = jsonObject.getAsString("description");
            boolean isPerceptronModel = (boolean) jsonObject.get("isPerceptronModel");
            BigDecimal error = (BigDecimal) jsonObject.get("error");

            Integer inNeurons = (Integer) jsonObject.getAsNumber("inNeurons");
            Integer hiddenNeurons = (Integer) jsonObject.getAsNumber("hiddenNeurons");
            Integer outNeurons = (Integer) jsonObject.getAsNumber("outNeurons");

            JSONArray inputColumnsJSON = (JSONArray) jsonObject.get("input_columns");
            JSONArray outColumnsJSON = (JSONArray) jsonObject.get("out_columns");

            List<String> inColumns = new ArrayList(inputColumnsJSON);
            List<String> outColumns = new ArrayList(outColumnsJSON);

            if (isPerceptronModel) {
                ActivationFunction hiddenActivationFunc = ActivationFunction.valueOf(jsonObject.getAsString("hiddenActivationFunction"));
                ActivationFunction outActivationFunc = ActivationFunction.valueOf(jsonObject.getAsString("outActivationFunction"));

                BasicNetwork network = getPerceptronNetworkFromJSON(jsonObject);
                Perceptron perceptron = new Perceptron(inNeurons, hiddenNeurons, outNeurons, network);
                perceptron.setHiddenActivationFunc(hiddenActivationFunc);
                perceptron.setOutActivationFunc(outActivationFunc);

                perceptron.setHiddenActivationFunc(hiddenActivationFunc);
                perceptron.setHiddenFuncFormatted(ActivationFunctionFormatterConverter.convert(hiddenActivationFunc));
                perceptron.setOutActivationFunc(outActivationFunc);
                perceptron.setOutFuncFormatted(ActivationFunctionFormatterConverter.convert(outActivationFunc));

                networkModel.setPerceptron(perceptron);
            } else {
                RBFNetwork network = getRBFNetworkFromJSON(jsonObject);
                RadialBasisFunctionsNetwork rbfNetwork = new RadialBasisFunctionsNetwork(inNeurons, hiddenNeurons, outNeurons, network);
                networkModel.setRbfNetwork(rbfNetwork);
            }

            networkModel.setName(name);
            networkModel.setDescription(description);
            networkModel.setPerceptronModel(isPerceptronModel);
            networkModel.setError(error.doubleValue());
            networkModel.setInputColumns(inColumns);
            networkModel.setOutColumns(outColumns);

            networkModel.setOwner(doctorService.getCurrentDoctor());
            networkModelService.save(networkModel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ModelParsingException("Неправильная структура файла", e);
        }
        return networkModel;
    }

    private String getTextualNetworkStructure(NetworkModel networkModel) throws IOException {
        File tmpFile = new File(new Date().getTime() + ".dmmy");
        tmpFile.createNewFile();
        EncogDirectoryPersistence.saveObject(tmpFile,
                networkModel.isPerceptronModel() ? networkModel.getPerceptron().getNetwork() : networkModel.getRbfNetwork().getNetwork());

        StringBuilder sb = new StringBuilder();
        Files.readAllLines(Paths.get(tmpFile.getPath())).forEach(sb::append);
        tmpFile.delete();
        return sb.toString();
    }

    private BasicNetwork getPerceptronNetworkFromJSON(JSONObject jsonObject) throws IOException {
        File file = buildTemporaryFileWithStructure(jsonObject);
        BasicNetwork network = (BasicNetwork) EncogDirectoryPersistence.loadObject(file);
        file.delete();
        return network;
    }

    private RBFNetwork getRBFNetworkFromJSON(JSONObject jsonObject) throws IOException {
        File file = buildTemporaryFileWithStructure(jsonObject);
        RBFNetwork network = (RBFNetwork) EncogDirectoryPersistence.loadObject(file);
        file.delete();
        return network;
    }

    private File buildTemporaryFileWithStructure(JSONObject jsonObject) throws IOException {
        String networkStructure = jsonObject.getAsString("network_structure");
        File tmpFile = new File(new Date().getTime() + ".dmmy");
        tmpFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(tmpFile);
        fos.write(networkStructure.getBytes());
        fos.flush();
        fos.close();
        return tmpFile;
    }
}
