package com.dev.service;

public interface NormalizationService {
    double[][] normalizeData(double[][] data, double actualHigh, double actualLow);

    double[] normalizeData(double[] data, double actualHigh, double actualLow);

    double[] normalizeData(double[] data, double actualHigh, double actualLow, double normHigh, double normLow);

    double normalizeData(double data, double actualHigh, double actualLow);

    double normalizeData(double data, double actualHigh, double actualLow, double normHigh, double normLow);

    double deNormalizeValue(double value, double actualHigh, double actualLow);

    double deNormalizeValue(double value, double actualHigh, double actualLow, double normHigh, double normLow);
}
