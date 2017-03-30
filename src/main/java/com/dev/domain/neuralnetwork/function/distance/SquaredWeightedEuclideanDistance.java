package com.dev.domain.neuralnetwork.function.distance;

import com.dev.domain.neuralnetwork.common.ArraysHelper;

public class SquaredWeightedEuclideanDistance implements DistanceFunction {
    private double weight;

    public SquaredWeightedEuclideanDistance() {
        this(1);
    }

    public SquaredWeightedEuclideanDistance(double weight) {
        this.weight = weight;
    }

    @Override
    public Double apply(double[] a, double[] b) {
        return weight * ArraysHelper.mergeByBiFunction(a, b, (x, y) -> (x - y) * (x - y)).sum();
    }

    @Override
    public String toString() {
        return "Squared Weighted Euclidean Distance (w = " + weight + ")";
    }
}
