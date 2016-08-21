package com.deckerchan.ml.classifier.k;

import com.deckerchan.ml.classifier.entities.Cluster;
import com.deckerchan.ml.classifier.entities.RealItemHDPoint;
import com.deckerchan.ml.classifier.utils.PointUtils;

import java.util.List;

public final class KMedoidsClassifier extends KClassifierBase {


    public KMedoidsClassifier(int kValue, List<RealItemHDPoint> itemPoints) {
        super(kValue, itemPoints);
    }

    @Override
    protected void doBalance() {
        for (Cluster cluster : this.getClusters()) {
            cluster.setCoordinate(
                    cluster
                            .getRelatedPointList()
                            .parallelStream()
                            .min(
                                    (p1, p2) ->
                                            Double.compare(
                                                    PointUtils.calculateTotalCost(p1, cluster.getRelatedPointList()),
                                                    PointUtils.calculateTotalCost(p2, cluster.getRelatedPointList())
                                            ))
                            .get().getCoordinate()
            );
        }
    }
}
