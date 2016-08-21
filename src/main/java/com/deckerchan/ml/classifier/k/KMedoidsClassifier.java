package com.deckerchan.ml.classifier.k;

import com.deckerchan.ml.classifier.entities.Cluster;
import com.deckerchan.ml.classifier.entities.RealItemHDPoint;
import com.deckerchan.ml.classifier.utils.PointUtils;

import java.util.List;

import static java.lang.System.out;

public final class KMedoidsClassifier extends KClassifierBase {


    public KMedoidsClassifier(int kValue, List<RealItemHDPoint> itemPoints) {
        super(kValue, itemPoints);
    }

    @Override
    protected void doBalance() {
        for (Cluster cluster :
                this.getClusters()) {
            cluster.setDimensionValueMap(
                    cluster
                            .getRelatedPointWithDistance()
                            .keySet().parallelStream()
                            .min(
                                    (p1, p2) ->
                                    {
                                        out.printf("Comparing %s and %s%n", p1.toString(), p2.toString());
                                        return Double.compare(
                                                PointUtils.calculateTotalCost(p1, cluster.getRelatedPointWithDistance().keySet()),
                                                PointUtils.calculateTotalCost(p2, cluster.getRelatedPointWithDistance().keySet())
                                        );
                                    })
                            .get().getDimensionValueMap()
            );
        }
    }
}
