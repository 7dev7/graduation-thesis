package com.dev.service.impl;

import com.dev.domain.dao.NetworkModelRepository;
import com.dev.domain.model.DTO.ComputeModelDataDTO;
import com.dev.domain.model.DTO.ComputeResultDTO;
import com.dev.domain.model.DTO.NetworkModelDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.network.Perceptron;
import com.dev.domain.model.network.RadialBasisFunctionsNetwork;
import com.dev.service.DoctorService;
import com.dev.service.NetworkModelService;
import com.dev.service.NormalizationService;
import org.apache.commons.lang3.ArrayUtils;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NetworkModelServiceImpl implements NetworkModelService {
    private final NetworkModelRepository networkModelRepository;
    private final NormalizationService normalizationService;
    private final DoctorService doctorService;

    @Autowired
    public NetworkModelServiceImpl(NetworkModelRepository networkModelRepository, DoctorService doctorService,
                                   NormalizationService normalizationService) {
        this.networkModelRepository = networkModelRepository;
        this.doctorService = doctorService;
        this.normalizationService = normalizationService;
    }

    @Override
    public void save(NetworkModel networkModel) {
        networkModel.setOwner(doctorService.getCurrentDoctor());
        networkModelRepository.save(networkModel);
    }

    @Override
    public void update(NetworkModelDTO modelDTO) {
        NetworkModel model = networkModelRepository.findOne(modelDTO.getId());
        if (model != null) {
            model.setName(modelDTO.getName());
            model.setDescription(modelDTO.getDescription());
        }
        networkModelRepository.save(model);
    }

    @Override
    public List<NetworkModel> getModelsForCurrentDoctor() {
        return networkModelRepository.findAllByOwner(doctorService.getCurrentDoctor());
    }

    @Override
    public void removeModelById(long id) {
        networkModelRepository.delete(id);
    }

    @Override
    public NetworkModel findById(long id) {
        return networkModelRepository.findOne(id);
    }

    @Override
    public ComputeResultDTO compute(ComputeModelDataDTO computeModelDataDTO) {
        NetworkModel model = findById(computeModelDataDTO.getModelId());
        if (model.isPerceptronModel()) {
            return computeMLP(model, computeModelDataDTO);
        } else {
            return computeRBF(model, computeModelDataDTO);
        }
    }

    private ComputeResultDTO computeMLP(NetworkModel model, ComputeModelDataDTO computeModelDataDTO) {
        double[] in = ArrayUtils.toPrimitive(computeModelDataDTO.getInputs().toArray(new Double[computeModelDataDTO.getInputs().size()]));
        Perceptron perceptron = model.getPerceptron();

        double[] normIn = new double[in.length];
        for (int i = 0; i < in.length; i++) {
            Double min = model.getMinIns().get(i);
            Double max = model.getMaxIns().get(i);
            double v = normalizationService.normalizeData(in[i], max, min);
            normIn[i] = v;
        }

        double[] out = new double[model.getPerceptron().getOutNeurons()];

        perceptron.getNetwork().compute(normIn, out);

        double[] normOut = new double[out.length];
        for (int i = 0; i < out.length; i++) {
            Double min = model.getMinOuts().get(i);
            Double max = model.getMaxOuts().get(i);
            double v = normalizationService.deNormalizeValue(out[i], max, min);
            normOut[i] = v;
        }
        List<Double> values = Arrays.asList(ArrayUtils.toObject(normOut));
        ComputeResultDTO resultDTO = new ComputeResultDTO();
        resultDTO.setValues(values);
        resultDTO.setInputValues(computeModelDataDTO.getInputs());
        resultDTO.setModelId(model.getId());
        resultDTO.setModelName(model.getName());

        resultDTO.setInColumns(model.getInputColumns());
        resultDTO.setOutColumns(model.getOutColumns());

        return resultDTO;
    }

    private ComputeResultDTO computeRBF(NetworkModel model, ComputeModelDataDTO computeModelDataDTO) {
        double[] in = ArrayUtils.toPrimitive(computeModelDataDTO.getInputs().toArray(new Double[computeModelDataDTO.getInputs().size()]));

        RadialBasisFunctionsNetwork rbfNetwork = model.getRbfNetwork();
        double[] normIn = new double[in.length];
        for (int i = 0; i < in.length; i++) {
            Double min = model.getMinIns().get(i);
            Double max = model.getMaxIns().get(i);
            double v = normalizationService.normalizeData(in[i], max, min);
            normIn[i] = v;
        }

        MLData compute = rbfNetwork.getNetwork().compute(new BasicMLData(normIn));
        double[] out = compute.getData();

        double[] normOut = new double[out.length];
        for (int i = 0; i < out.length; i++) {
            Double min = model.getMinOuts().get(i);
            Double max = model.getMaxOuts().get(i);
            double v = normalizationService.deNormalizeValue(out[i], max, min);
            normOut[i] = v;
        }
        List<Double> values = Arrays.asList(ArrayUtils.toObject(normOut));
        ComputeResultDTO resultDTO = new ComputeResultDTO();
        resultDTO.setValues(values);
        resultDTO.setInputValues(computeModelDataDTO.getInputs());
        resultDTO.setModelId(model.getId());
        resultDTO.setModelName(model.getName());

        resultDTO.setInColumns(model.getInputColumns());
        resultDTO.setOutColumns(model.getOutColumns());

        return resultDTO;
    }
}
