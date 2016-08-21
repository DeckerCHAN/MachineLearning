package com.deckerchan.ml.classifier.utils;

import com.deckerchan.ml.classifier.entities.Dimension;
import com.deckerchan.ml.classifier.entities.DimensionValueMap;
import com.deckerchan.ml.classifier.entities.HyperDimensionPoint;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static java.lang.System.out;

public class PointUtils {
    public static Double pointDistance(HyperDimensionPoint pointA, HyperDimensionPoint pointB) {
        DimensionValueMap mapA = pointA.getDimensionValueMap();
        DimensionValueMap mapB = pointB.getDimensionValueMap();

        if (!mapA.keySet().equals(mapB.keySet())) {
            throw new RuntimeException("Two points has different dimensions.");
        }

        Set<Dimension> keys = mapA.keySet();

        double sum = 0D;


        for (Dimension dimension : keys) {
            double currentDimensionValueA = mapA.get(dimension);
            double currentDimensionValueB = mapB.get(dimension);
            sum += Math.pow((currentDimensionValueB - currentDimensionValueA), 2) * dimension.getWeight();
        }

        Double sqrt = Math.sqrt(sum);
        //out.printf("Distance from %s to %s is %f %n", pointA, pointB, sqrt);
        return sqrt;

    }

    public static Double calculateTotalCost(HyperDimensionPoint current, Collection<HyperDimensionPoint> others) {
        return others.parallelStream().mapToDouble(p -> current.getDistanceFrom(p)).sum();
    }

}
