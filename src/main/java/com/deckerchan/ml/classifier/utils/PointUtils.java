package com.deckerchan.ml.classifier.utils;

import com.deckerchan.ml.classifier.entities.Coordinate;
import com.deckerchan.ml.classifier.entities.Dimension;
import com.deckerchan.ml.classifier.entities.HyperDimensionPoint;

import java.util.Collection;
import java.util.Set;

public class PointUtils {
    public static Double eulaDistance(HyperDimensionPoint pointA, HyperDimensionPoint pointB) {
        Coordinate mapA = pointA.getCoordinate();
        Coordinate mapB = pointB.getCoordinate();

        if (!mapA.keySet().equals(mapB.keySet())) {
            throw new RuntimeException("Two points has different dimensions.");
        }

        Set<Dimension> keys = mapA.keySet();

        double sum = 0D;


        for (Dimension dimension : keys) {
            double currentDimensionValueA = mapA.get(dimension);
            double currentDimensionValueB = mapB.get(dimension);
            sum += Math.pow((currentDimensionValueB - currentDimensionValueA), 2);
        }

        Double sqrt = Math.sqrt(sum);
        //out.printf("Distance from %s to %s is %f %n", pointA, pointB, sqrt);
        return sqrt;

    }

    public static Double calculateTotalCost(HyperDimensionPoint current, Collection<HyperDimensionPoint> others) {
        return others.parallelStream().mapToDouble(current::getDistanceFrom).sum();
    }

    public static double cosineSimilarity(HyperDimensionPoint pointA, HyperDimensionPoint PointB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (Dimension dimension : pointA.getCoordinate().getDimensions()) {
            dotProduct += pointA.getCoordinate().get(dimension) * PointB.getCoordinate().get(dimension);
            normA += Math.pow(pointA.getCoordinate().get(dimension), 2);
            normB += Math.pow(PointB.getCoordinate().get(dimension), 2);
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

}
