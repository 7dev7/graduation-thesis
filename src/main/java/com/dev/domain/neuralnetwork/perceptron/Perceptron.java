package com.dev.domain.neuralnetwork.perceptron;

import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;

import java.util.List;

public class Perceptron {
    private BasicNetwork network = new BasicNetwork();

    public Perceptron(List<BasicLayer> layers) {
        layers.forEach(l -> network.addLayer(l));
        network.getStructure().finalizeStructure();
        network.reset();
    }
}
