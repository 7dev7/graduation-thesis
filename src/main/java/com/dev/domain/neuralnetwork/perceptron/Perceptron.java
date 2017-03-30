package com.dev.domain.neuralnetwork.perceptron;

public interface Perceptron {
    double[] process(double[] input);

    void study(double[] input, double[] expectedOutput);

    double[] getNeurons(int layer);

    int getLayers();
}
