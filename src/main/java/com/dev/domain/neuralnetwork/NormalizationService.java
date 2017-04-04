package com.dev.domain.neuralnetwork;

import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import org.springframework.stereotype.Service;

@Service
public class NormalizationService {

    public double[] normalizeData(double[] data, double actualHigh, double actualLow) {
        return normalizeData(data, actualHigh, actualLow, 1, 0);
    }

    public double[] normalizeData(double[] data, double actualHigh, double actualLow, double normHigh, double normLow) {
        NormalizedField normalizedField = new NormalizedField(NormalizationAction.Normalize, "normalize",
                actualHigh, actualLow, normHigh, normLow);
        double[] normalizedData = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            normalizedData[i] = normalizedField.normalize(data[i]);
        }
        return normalizedData;
    }

    public double deNormalizeValue(double value, double actualHigh, double actualLow, double normHigh, double normLow) {
        NormalizedField normalizedField = new NormalizedField(NormalizationAction.Normalize, "normalize",
                actualHigh, actualLow, normHigh, normLow);
        return normalizedField.deNormalize(value);
    }
}
