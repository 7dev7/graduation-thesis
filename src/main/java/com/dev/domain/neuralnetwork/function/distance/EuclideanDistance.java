package com.dev.domain.neuralnetwork.function.distance;

import com.dev.domain.neuralnetwork.common.ArraysHelper;

public class EuclideanDistance implements DistanceFunction {

    @Override
    public Double apply(double[] a, double[] b) {
        double squareEuclideanDistance = ArraysHelper.mergeByBiFunction(a, b, (x, y) -> (x - y) * (x - y)).sum();
        return Math.sqrt(squareEuclideanDistance);
    }

    @Override
    public String toString() {
        return "Euclidean Distance";
    }
}
