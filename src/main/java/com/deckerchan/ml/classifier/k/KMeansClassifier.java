package com.deckerchan.ml.classifier.k;

import com.deckerchan.ml.classifier.entities.Cluster;
import com.deckerchan.ml.classifier.entities.Coordinate;
import com.deckerchan.ml.classifier.entities.Dimension;
import com.deckerchan.ml.classifier.entities.RealItemHDPoint;

import java.util.List;

public final class KMeansClassifier extends KClassifierBase {

    public KMeansClassifier(int kValue, List<RealItemHDPoint> itemPoints) {
        super(kValue, itemPoints);
    }

    @Override
    protected void doBalance() {
        for (Cluster cluster : this.getClusters()) {
            Coordinate newCoordinate = new Coordinate(cluster.getCoordinate().keySet());
            for (Dimension dimension : newCoordinate.getDimensions()) {
                Double sum = cluster.getRelatedPointList().stream().mapToDouble(hyperDimensionPoint -> hyperDimensionPoint.getCoordinate().get(dimension)).sum();
                Double extent = sum / cluster.getRelatedPointList().size();
                newCoordinate.set(dimension, extent);
            }
            cluster.setCoordinate(newCoordinate);
        }
    }
}
