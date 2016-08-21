package com.deckerchan.ml.classifier.utils;

import com.deckerchan.ml.classifier.entities.Dimension;
import com.deckerchan.ml.classifier.entities.HyperDimensionPoint;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class PointUtils {
    public static Double pointDistance(HyperDimensionPoint pointA, HyperDimensionPoint pointB) {
        Map<Dimension, Long> mapA = pointA.getDimensionValueMap();
        Map<Dimension, Long> mapB = pointB.getDimensionValueMap();

        if (!mapA.keySet().equals(mapB.keySet())) {
            throw new RuntimeException("Two points has different dimensions.");
        }

        Set<Dimension> keys = mapA.keySet();

        double sum = 0D;


        for (Dimension dimension : keys) {
            long currentDimensionValueA = mapA.get(dimension);
            long currentDimensionValueB = mapB.get(dimension);
            sum += Math.pow((currentDimensionValueB - currentDimensionValueA), 2) * dimension.getWeight();
        }

        return Math.sqrt(sum);

    }
    public static Double calculateTotalCost(HyperDimensionPoint current, Collection<HyperDimensionPoint> others)
    {
        return others.parallelStream().mapToDouble(p-> pointDistance(p,current)).sum();
    }

}
