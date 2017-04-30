package com.dev.domain.neuralnetwork.perceptron;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;


public class Perceptron {
    private BasicNetwork network;

    public Perceptron(int inNeurons, int neuronsOnHiddenLayer, int outNeurons) {
        network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, inNeurons));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, neuronsOnHiddenLayer));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false, outNeurons));
        network.getStructure().finalizeStructure();
        network.reset();
    }

    public BasicNetwork getNetwork() {
        return network;
    }

    public void setNetwork(BasicNetwork network) {
        this.network = network;
    }
}
