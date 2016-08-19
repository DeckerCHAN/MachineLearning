package com.deckerchan.ml.classfier.utils;

import com.deckerchan.ml.classfier.entities.Dimension;
import com.deckerchan.ml.classfier.entities.HyperDimensionPoint;

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
            sum += Math.pow((currentDimensionValueB - currentDimensionValueA), 2) * ((double) dimension.getWeight() / sumWeight);
        }

        return Math.sqrt(sum);

    }
}
