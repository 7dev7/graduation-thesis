package com.dev.domain.model.network;

import com.dev.domain.model.ActivationFunction;
import org.encog.engine.network.activation.ActivationSIN;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationStep;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;

import java.io.Serializable;


public class Perceptron implements Serializable {
    private BasicNetwork network;
    private ActivationFunction hiddenActivationFunc;
    private ActivationFunction outActivationFunc;
    private int inputNeurons;
    private int hiddenNeurons;
    private int outNeurons;

    private String hiddenFuncFormatted;
    private String outFuncFormatted;

    public Perceptron(int inNeurons, int neuronsOnHiddenLayer, int outNeurons) {
        this.inputNeurons = inNeurons;
        this.hiddenNeurons = neuronsOnHiddenLayer;
        this.outNeurons = outNeurons;

        network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, inNeurons));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, neuronsOnHiddenLayer));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false, outNeurons));
        network.getStructure().finalizeStructure();
        network.reset();
    }

    public Perceptron(int inNeurons, int neuronsOnHiddenLayer, int outNeurons, BasicNetwork network) {
        this.inputNeurons = inNeurons;
        this.hiddenNeurons = neuronsOnHiddenLayer;
        this.outNeurons = outNeurons;
        this.network = network;
    }

    public Perceptron(int inNeurons, int neuronsOnHiddenLayer, int outNeurons, ActivationFunction hiddenActivationFunc,
                      ActivationFunction outActivationFunc) {
        this.inputNeurons = inNeurons;
        this.hiddenNeurons = neuronsOnHiddenLayer;
        this.outNeurons = outNeurons;
        this.hiddenActivationFunc = hiddenActivationFunc;
        this.outActivationFunc = outActivationFunc;
        network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, inNeurons));
        network.addLayer(new BasicLayer(transform(hiddenActivationFunc), true, neuronsOnHiddenLayer));
        network.addLayer(new BasicLayer(transform(outActivationFunc), false, outNeurons));
        network.getStructure().finalizeStructure();
        network.reset();
    }

    private org.encog.engine.network.activation.ActivationFunction transform(ActivationFunction activationFunction) {
        org.encog.engine.network.activation.ActivationFunction res = new ActivationSigmoid();

        switch (activationFunction) {
            case LOGISTIC:
                res = new ActivationSigmoid();
                break;
            case HYPERBOLIC:
                res = new ActivationTANH();
                break;
            case EXP:
                res = new ActivationStep();
                break;
            case SIN:
                res = new ActivationSIN();
                break;
        }
        return res;
    }

    public BasicNetwork getNetwork() {
        return network;
    }

    public void setNetwork(BasicNetwork network) {
        this.network = network;
    }

    public ActivationFunction getHiddenActivationFunc() {
        return hiddenActivationFunc;
    }

    public void setHiddenActivationFunc(ActivationFunction hiddenActivationFunc) {
        this.hiddenActivationFunc = hiddenActivationFunc;
    }

    public ActivationFunction getOutActivationFunc() {
        return outActivationFunc;
    }

    public void setOutActivationFunc(ActivationFunction outActivationFunc) {
        this.outActivationFunc = outActivationFunc;
    }

    public int getInputNeurons() {
        return inputNeurons;
    }

    public void setInputNeurons(int inputNeurons) {
        this.inputNeurons = inputNeurons;
    }

    public int getHiddenNeurons() {
        return hiddenNeurons;
    }

    public void setHiddenNeurons(int hiddenNeurons) {
        this.hiddenNeurons = hiddenNeurons;
    }

    public int getOutNeurons() {
        return outNeurons;
    }

    public void setOutNeurons(int outNeurons) {
        this.outNeurons = outNeurons;
    }

    public String getHiddenFuncFormatted() {
        return hiddenFuncFormatted;
    }

    public void setHiddenFuncFormatted(String hiddenFuncFormatted) {
        this.hiddenFuncFormatted = hiddenFuncFormatted;
    }

    public String getOutFuncFormatted() {
        return outFuncFormatted;
    }

    public void setOutFuncFormatted(String outFuncFormatted) {
        this.outFuncFormatted = outFuncFormatted;
    }

    @Override
    public String toString() {
        return "Perceptron{" +
                "hiddenActivationFunc=" + hiddenActivationFunc +
                ", outActivationFunc=" + outActivationFunc +
                ", inputNeurons=" + inputNeurons +
                ", hiddenNeurons=" + hiddenNeurons +
                ", outNeurons=" + outNeurons +
                ", hiddenFuncFormatted='" + hiddenFuncFormatted + '\'' +
                ", outFuncFormatted='" + outFuncFormatted + '\'' +
                '}';
    }
}
