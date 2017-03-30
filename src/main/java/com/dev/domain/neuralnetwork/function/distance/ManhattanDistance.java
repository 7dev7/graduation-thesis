package com.dev.domain.neuralnetwork.function.distance;

import com.dev.domain.neuralnetwork.common.ArraysHelper;

public class ManhattanDistance implements DistanceFunction {
    @Override
    public Double apply(double[] a, double[] b) {
        return ArraysHelper.mergeByBiFunction(a, b, (x, y) -> Math.abs(x - y)).sum();
    }

    @Override
    public String toString() {
        return "Manhattan Distance";
    }
}
