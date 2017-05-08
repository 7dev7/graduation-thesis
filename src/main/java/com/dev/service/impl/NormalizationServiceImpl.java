package com.dev.service.impl;

import com.dev.service.NormalizationService;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import org.springframework.stereotype.Service;

@Service
public class NormalizationServiceImpl implements NormalizationService {

    @Override
    public double[][] normalizeData(double[][] data, double actualHigh, double actualLow) {
        double[][] res = new double[data.length][];
        for (int i = 0; i < data.length; i++) {
            double[] item = data[i];
            double[] normalized = normalizeData(item, actualHigh, actualLow, 1, 0);
            res[i] = normalized;
        }
        return res;
    }

    @Override
    public double[] normalizeData(double[] data, double actualHigh, double actualLow) {
        return normalizeData(data, actualHigh, actualLow, 1, 0);
    }

    @Override
    public double[] normalizeData(double[] data, double actualHigh, double actualLow, double normHigh, double normLow) {
        NormalizedField normalizedField = new NormalizedField(NormalizationAction.Normalize, "normalize",
                actualHigh, actualLow, normHigh, normLow);
        double[] normalizedData = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            normalizedData[i] = normalizedField.normalize(data[i]);
        }
        return normalizedData;
    }

    @Override
    public double normalizeData(double data, double actualHigh, double actualLow) {
        return normalizeData(data, actualHigh, actualLow, 1, 0);
    }

    @Override
    public double normalizeData(double data, double actualHigh, double actualLow, double normHigh, double normLow) {
        NormalizedField normalizedField = new NormalizedField(NormalizationAction.Normalize, "normalize",
                actualHigh, actualLow, normHigh, normLow);
        return normalizedField.normalize(data);
    }

    @Override
    public double deNormalizeValue(double value, double actualHigh, double actualLow) {
        return deNormalizeValue(value, actualHigh, actualLow, 1, 0);
    }

    @Override
    public double deNormalizeValue(double value, double actualHigh, double actualLow, double normHigh, double normLow) {
        NormalizedField normalizedField = new NormalizedField(NormalizationAction.Normalize, "normalize",
                actualHigh, actualLow, normHigh, normLow);
        return normalizedField.deNormalize(value);
    }
}
