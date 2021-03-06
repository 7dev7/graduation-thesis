package com.dev.domain.model.network;

import org.encog.mathutil.rbf.RBFEnum;
import org.encog.neural.rbf.RBFNetwork;

import java.io.Serializable;

public class RadialBasisFunctionsNetwork implements Serializable {
    private RBFNetwork network;
    private int inputNeurons;
    private int hiddenNeurons;
    private int outNeurons;

    public RadialBasisFunctionsNetwork(int inputNeurons, int hiddenNeurons, int outNeurons) {
        this.inputNeurons = inputNeurons;
        this.hiddenNeurons = hiddenNeurons;
        this.outNeurons = outNeurons;

        this.network = new RBFNetwork(inputNeurons, hiddenNeurons, outNeurons, RBFEnum.Gaussian);
        this.network.reset();
    }

    public RadialBasisFunctionsNetwork(int inputNeurons, int hiddenNeurons, int outNeurons, RBFNetwork network) {
        this.network = network;
        this.inputNeurons = inputNeurons;
        this.hiddenNeurons = hiddenNeurons;
        this.outNeurons = outNeurons;
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

    @Override
    public String toString() {
        return "RadialBasisFunctionsNetwork{" +
                "inputNeurons=" + inputNeurons +
                ", hiddenNeurons=" + hiddenNeurons +
                ", outNeurons=" + outNeurons +
                '}';
    }
}
