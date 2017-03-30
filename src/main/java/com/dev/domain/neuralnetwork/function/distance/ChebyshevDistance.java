package com.dev.domain.neuralnetwork.function.distance;

import com.dev.domain.neuralnetwork.common.ArraysHelper;

import java.util.OptionalDouble;

public class ChebyshevDistance implements DistanceFunction {
    @Override
    public Double apply(double[] a, double[] b) {
        OptionalDouble max = ArraysHelper.mergeByBiFunction(a, b, (x, y) -> Math.abs(x - y)).max();
        return max.isPresent() ? max.getAsDouble() : 0;
    }

    @Override
    public String toString() {
        return "Chebyshev Distance";
    }
}