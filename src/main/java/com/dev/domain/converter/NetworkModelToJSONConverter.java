package com.dev.domain.converter;

import com.dev.domain.model.NetworkModel;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import org.encog.persist.EncogDirectoryPersistence;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class NetworkModelToJSONConverter {

    public static String convert(NetworkModel networkModel) {
        JSONObject jsonObject = new JSONObject();
        try {
            File tmpFile = new File(new Date().getTime() + ".dmmy");
            tmpFile.createNewFile();
            EncogDirectoryPersistence.saveObject(tmpFile,
                    networkModel.isPerceptronModel() ? networkModel.getPerceptron().getNetwork() : networkModel.getRbfNetwork().getNetwork());

            StringBuilder sb = new StringBuilder();
            Files.readAllLines(Paths.get(tmpFile.getPath())).forEach(sb::append);
            tmpFile.delete();

            jsonObject.put("network_structure", sb.toString());
            jsonObject.put("name", networkModel.getName());
            jsonObject.put("description", networkModel.getDescription());
            jsonObject.put("isPerceptronModel", networkModel.isPerceptronModel());
            jsonObject.put("error", networkModel.getError());
            jsonObject.put("hiddenActivationFunction", networkModel.getHiddenActivationFunction());
            jsonObject.put("outActivationFunction", networkModel.getOutActivationFunction());

            return new org.json.JSONObject(jsonObject.toJSONString(JSONStyle.NO_COMPRESS)).toString(2);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toJSONString(JSONStyle.NO_COMPRESS);
    }
}
