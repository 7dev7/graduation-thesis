package com.dev.domain.model.network;

import org.encog.mathutil.rbf.RBFEnum;
import org.encog.neural.rbf.RBFNetwork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RadialBasisFunctionsNetwork implements Serializable {
    private RBFNetwork network;
    private int inputNeurons;
    private int hiddenNeurons;
    private int outNeurons;

    private List<Double> maxIns;
    private List<Double> minIns;

    private List<Double> maxOuts;
    private List<Double> minOuts;

    public RadialBasisFunctionsNetwork(int inputNeurons, int hiddenNeurons, int outNeurons) {
        this.inputNeurons = inputNeurons;
        this.hiddenNeurons = hiddenNeurons;
        this.outNeurons = outNeurons;

        this.maxIns = new ArrayList<>();
        this.minIns = new ArrayList<>();

        maxOuts = new ArrayList<>();
        minOuts = new ArrayList<>();

        network = new RBFNetwork(inputNeurons, hiddenNeurons, outNeurons, RBFEnum.Gaussian);
        network.reset();
    }

    public RBFNetwork getNetwork() {
        return network;
    }

    public void setNetwork(RBFNetwork network) {
        this.network = network;
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

    public List<Double> getMaxIns() {
        return maxIns;
    }

    public void setMaxIns(List<Double> maxIns) {
        this.maxIns = maxIns;
    }

    public List<Double> getMinIns() {
        return minIns;
    }

    public void setMinIns(List<Double> minIns) {
        this.minIns = minIns;
    }

    public List<Double> getMaxOuts() {
        return maxOuts;
    }

    public void setMaxOuts(List<Double> maxOuts) {
        this.maxOuts = maxOuts;
    }

    public List<Double> getMinOuts() {
        return minOuts;
    }

    public void setMinOuts(List<Double> minOuts) {
        this.minOuts = minOuts;
    }
}
