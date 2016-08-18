package com.deckerchan.ml.cluster.utils;

import com.deckerchan.ml.cluster.entities.Dimension;
import com.deckerchan.ml.cluster.entities.HyperDimensionPoint;

import java.util.Map;
import java.util.Set;

public class PointUtils {
    public static Double pointDistance(HyperDimensionPoint pointA, HyperDimensionPoint pointB) {
        Map<Dimension, Double> mapA = pointA.getDimensionValueMap();
        Map<Dimension, Double> mapB = pointB.getDimensionValueMap();

        if (!mapA.keySet().equals(mapB.keySet())) {
            throw new RuntimeException("Two points has different dimensions.");
        }

        Set<Dimension> keys = mapA.keySet();

        double sum = 0D;

        int sumWeight = keys.stream().mapToInt(Dimension::getWeight).sum();

        for (Dimension dimension : keys) {
            Double currentDimensionValueA = mapA.get(dimension);
            Double currentDimensionValueB = mapB.get(dimension);
            sum += Math.pow((currentDimensionValueB - currentDimensionValueA) * ((double) dimension.geteight() / sumWeight), 2);
        }

        return Math.sqrt(sum);

    }
}
