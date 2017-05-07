package com.dev;

import com.dev.service.PerceptronTrainingService;
import com.dev.service.impl.BackpropagationTrainingService;
import com.dev.service.impl.NormalizationService;
import org.junit.Test;

public class PerceptronTest {

    private NormalizationService normalizationService = new NormalizationService();
    private PerceptronTrainingService trainingService = new BackpropagationTrainingService();

    private double[][] in = {
            new double[]{0.98},
            new double[]{0.89},
            new double[]{0.91},
            new double[]{0.94},
            new double[]{0.93},
            new double[]{0.73},
            new double[]{0.91},
            new double[]{0.86},
            new double[]{0.98},
            new double[]{0.99},
            new double[]{0.98},
            new double[]{0.97},
    };

    private double[][] out = {
            new double[]{8.1},
            new double[]{37.93},
            new double[]{41.67},
            new double[]{31.03},
            new double[]{32.43},
            new double[]{69.44},
            new double[]{26.67},
            new double[]{45.45},
            new double[]{8.1},
            new double[]{5.4},
            new double[]{7.8},
            new double[]{9.3},
    };


    @Test
    public void test() {
//        Perceptron perceptron = new Perceptron(1, 8, 1);
//        double[][] normalizedIn = normalizationService.normalizeData(in, 1, 0);
//        double[][] normalizedOut = normalizationService.normalizeData(out, 100, 0);
//
//        MLDataSet dataSet = new BasicNeuralDataSet(normalizedIn, normalizedOut);
//        double error = trainingService.train(perceptron, dataSet);
//        System.out.println("Error: " + error);
//
//        double testIn = normalizationService.normalizeData(0.98, 1, 0);
//        double[] testInArr = {testIn};
//        double[] outArr = new double[1];
//
//        perceptron.getNetwork().compute(testInArr, outArr);
//        System.out.println(normalizationService.deNormalizeValue(outArr[0], 100, 0));
    }
}
